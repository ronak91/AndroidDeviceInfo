package com.FiftyOneDegree.research.DeviceConfig

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.FiftyOneDegree.research.R
import kotlinx.android.synthetic.main.activity_device_config.*
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile


class DeviceConfigActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_config)


        txt_currentMaxCPUFreqMHz.text =  getCPUMaximumFrequency().toString()
        txt_availableMaxCPUFreqMHz.text = getMaxCPUFreqMHz().toString()
        txt_availableMaxCPUFreqGHz.text = getCPUMaximumFrequencyGHz().toString()
        txt_availableCPUInfo.text = ReadCPUinfo().toString()
        txt_newCPUInfo.text = ReadCPUinfo().toString()

    }

    private fun getCPUMaximumFrequencyGHz(): String? {
        var cpuMaxFreq = ""
        var roundedFreq = 0.0
        var cpuMaxFreqMHz = 0.0
        var cpuMaxFreqGhz = 0.0
        var reader: RandomAccessFile? = null
        try {
            reader = RandomAccessFile(
                "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq",
                "r"
            )
            cpuMaxFreq = reader.readLine()
            cpuMaxFreqMHz = cpuMaxFreq.toDouble() / 1000;
            cpuMaxFreqGhz = cpuMaxFreq.toDouble() / 1000000;

            roundedFreq =  Math.round(cpuMaxFreqGhz *10)/ 10.0
            Log.e("CPU", "Max Frequency: "+ roundedFreq)

            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return roundedFreq.toString()+" GHZ"
    }



    private fun getCPUMaximumFrequency(): String? {
        var cpuMaxFreq = ""
        var cpuMaxFreqMHz = 0.0
        var cpuMaxFreqGhz = 0.0
        var reader: RandomAccessFile? = null
        try {
            reader = RandomAccessFile(
                "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq",
                "r"
            )
            cpuMaxFreq = reader.readLine()
            cpuMaxFreqMHz = cpuMaxFreq.toDouble() / 1000;
            cpuMaxFreqGhz = cpuMaxFreq.toDouble() / 1000000;

            val ceiled =  Math.ceil(cpuMaxFreqGhz *10)/ 10
            Log.e("CPU", "Max Frequency MHZ : "+ ceiled)

            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return "cpuMaxFreq in KHz : "+  SystemUtils.getCPUFrequencyMax() +"\ncpuMaxFreq MHz : "+cpuMaxFreqMHz +"\nMax Frequency GHz : " + cpuMaxFreqGhz
    }

    private fun getCPUMaximumFrequency1(): String? {
        var cpuMaxFreq = ""
        var cpuMaxFreqMHZ = 0.0
        var reader: RandomAccessFile? = null
        try {
            reader = RandomAccessFile(
                "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq",
                "r"
            )
            cpuMaxFreq = reader.readLine()

            cpuMaxFreqMHZ = cpuMaxFreq.toDouble() / 1000000;

            Log.e("CPU", "Max Frequency MHZ : "+ cpuMaxFreqMHZ)

            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return "cpuMaxFreq : "+cpuMaxFreq +" Max Frequency MHZ : " + cpuMaxFreqMHZ
    }

    /**
     * Get max cpu rate.
     *
     * This works by examining the list of CPU frequencies in the pseudo file
     * "/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state" and how much time has been spent
     * in each. It finds the highest non-zero time and assumes that is the maximum frequency (note
     * that sometimes frequencies higher than that which was designed can be reported.) So it is not
     * impossible that this method will return an incorrect CPU frequency.
     *
     * Also note that (obviously) this will not reflect different CPU cores with different
     * maximum speeds.
     *
     * @return cpu frequency in MHz
     */
    fun getMaxCPUFreqMHz(): Int {
        var maxFreq = -1
        try {
            val reader =
                RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state", "r")
            var done = false
            while (!done) {
                val line: String = reader.readLine()
                if (null == line) {
                    done = true
                    break
                }
                val splits = line.split(" ")

                val timeInState = splits[0].toInt()
                if (timeInState > 0) {
                    val freq = splits[0].toInt() / 1000
                    if (freq > maxFreq) {
                        maxFreq = freq
                        done = true
                    }
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return maxFreq
    }


    private fun ReadCPUinfo(): String? {
        val cmd: ProcessBuilder
        var result = ""
        try {
            val args = arrayOf("/system/bin/cat", "/proc/cpuinfo")
            cmd = ProcessBuilder(*args)
            val process = cmd.start()
            val `in`: InputStream = process.inputStream
            val re = ByteArray(1024)
            while (`in`.read(re) !== -1) {
                println(String(re))
                result = result + String(re)
            }
            `in`.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return result
    }
}