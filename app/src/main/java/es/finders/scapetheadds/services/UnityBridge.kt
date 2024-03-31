package es.finders.scapetheadds.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class UnityBridge : Service() {

    private val TAG = "UnityBridge"
    private val PORT = 8189

    private lateinit var serverSocket: ServerSocket
    private lateinit var clientSocket: Socket
    private lateinit var out: PrintWriter
    private lateinit var inStream: BufferedReader

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service onCreate")
        startSocketServer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service onStartCommand")
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private fun startSocketServer() {
        Thread {
            try {
                serverSocket = ServerSocket(PORT)
                Log.d(TAG, "Server listening on port $PORT")

                clientSocket = serverSocket.accept()
                out = PrintWriter(clientSocket.getOutputStream(), true)
                inStream = BufferedReader(InputStreamReader(clientSocket.getInputStream()))

                // Preload the initial message (choose either infinite mode or level mode)
                val initialMessage = "{\"gamemode\": \"infinite\"}"
                out.println(initialMessage)

                // Listen for incoming messages
                while (true) {
                    val receivedMessage = inStream.readLine()
                    if (receivedMessage != null) {
                        Log.d(TAG, "Received: $receivedMessage")
                        // Handle the received message (e.g., parse JSON, trigger actions)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in socket server: ${e.message}")
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            serverSocket.close()
            clientSocket.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error closing sockets: ${e.message}")
        }
    }
}