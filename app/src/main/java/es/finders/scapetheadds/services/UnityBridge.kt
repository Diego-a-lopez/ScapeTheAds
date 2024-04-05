package es.finders.scapetheadds.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket

class UnityBridge : Service() {

    private val TAG = "UnityBridge"
    private val PORT = 8080

    private var mode = JSONObject().apply {
        put("gamemode", "infinite")
    }

    private fun getMode(): JSONObject {
        return mode
    }

    private fun setMode(newMode: JSONObject) {
        this.mode = newMode
    }


    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service onStartCommand")

        // Start the server in a separate thread
        Thread { startServer() }.start()

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private fun startServer() {
        try {
            val serverSocket = ServerSocket(PORT)
            Log.d(TAG, "Server listening on port $PORT")

            while (true) {
                val clientSocket = serverSocket.accept()
                Log.d(TAG, "Client connected: ${clientSocket.inetAddress}")

                // Send data to the client
                sendData(clientSocket, mode.toString())

                // Receive data from the client
                val receivedData = receiveData(clientSocket)
                Log.d(TAG, "Received data: $receivedData")

                clientSocket.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun sendData(socket: Socket, data: String) {
        val outputStream: OutputStream = socket.getOutputStream()
        outputStream.write(data.toByteArray())
        outputStream.flush()
    }

    private fun receiveData(socket: Socket): String {
        val inputStream: InputStream = socket.getInputStream()
        val buffer = ByteArray(1024)
        val bytesRead = inputStream.read(buffer)
        return String(buffer, 0, bytesRead)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service onDestroy")
    }
}