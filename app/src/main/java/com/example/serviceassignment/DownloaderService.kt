package com.example.serviceassignment

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

const val TAG = "service"

@AndroidEntryPoint
class DownloaderService : Service() {

    @Inject
    lateinit var repo : ProgressRepository

    private var startJob  : Job?   = null
    private var serviceStartId : Int?   = null
    private var serviceEndId   : Int?   = null
    private var jobList      = HashMap<Int,Job>()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val lastProgress = intent?.getIntExtra("lastProgress",0)
        val isStart      = intent?.getBooleanExtra("isStart",false)
        serviceStartId   = intent?.getIntExtra("startId",-1)

        if (isStart == true)
            startDownload(serviceStartId,lastProgress)
        else
            jobList[serviceStartId]?.cancel()


        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
      //  jobList[serviceEndId]?.cancel()
        Log.d("TAG", "onDestroy: Job ${serviceEndId} has been cancelled on $jobList")
        super.onDestroy()
    }

    private fun startDownload(serviceId: Int?, lastProgress: Int?) {
            startJob = CoroutineScope(Dispatchers.IO).launch {
                for (i in lastProgress!!..100) {
                    repo.updateProgress(i, serviceId!!)
                    delay(1000)
                }
                stopSelf()
            }

        if (serviceId != null) {
            jobList[serviceId] = startJob!!
        }
    }
}