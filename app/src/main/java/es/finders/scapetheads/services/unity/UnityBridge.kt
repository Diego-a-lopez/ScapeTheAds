package es.finders.scapetheads.services.unity

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    fun getMode(): JSONObject {
        Log.d(TAG, "Retrieved mode")
        return mode
    }

    fun setInfinite() {
        setMode(JSONObject().apply {
            put("gamemode", "infinite")
        })
    }

    fun setLevel(level: Int) {
        setMode(JSONObject().apply {
            put("gamemode", "level")
            put("gamemode", level)
        })
    }

    fun setMode(newMode: JSONObject) {
        Log.d(TAG, "Set mode")
        this.mode = newMode
    }
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service onStartCommand")

        Thread { startServer() }.start()

        return START_STICKY
    }

    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): UnityBridge = this@UnityBridge
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
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

                handleData(receivedData)
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

    @OptIn(DelicateCoroutinesApi::class)
    private fun handleData(data: String) {
        GlobalScope.launch {
            println(data) // TODO: Finish
            // If infinite mode score
            // TODO: Store in local room score
            // TODO: Send to firestore data if highscore > current user highscore
            // If level
            // TODO: Update in room level completion
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service onDestroy")
    }
}