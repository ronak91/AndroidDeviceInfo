package com.FiftyOneDegree.research.Storage;

import android.app.ActivityManager;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.FiftyOneDegree.research.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StorageActivity extends AppCompatActivity {


    TextView txt_availableStorage, txt_totalStorage, txt_availableInternalStorage, txt_totalInternalStorage, txt_availableMemory, txt_totalMemory, txt_isSDCard, txt_ReadCPUinfo;

    StatFs stat1 = new StatFs(Environment.getExternalStorageDirectory().getPath());

    private static final long KILOBYTE = 1024;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        txt_availableStorage = findViewById(R.id.txt_availableStorage);
        txt_totalStorage = findViewById(R.id.txt_usedStorage);
        txt_availableInternalStorage = findViewById(R.id.txt_availableInternalStorage);
        txt_totalInternalStorage = findViewById(R.id.txt_totalInternalStorage);
        txt_availableMemory = findViewById(R.id.txt_availableMemory);
        txt_totalMemory = findViewById(R.id.txt_usedMemory);
        txt_isSDCard = findViewById(R.id.txt_isSDCard);
        txt_ReadCPUinfo = findViewById(R.id.txt_ReadCPUinfo);

        getExternalStorage();

        getExternalStorage123();

        method1();

        String InternalFreeSpace = convertBytes(getInternalFreeSpace());
        Log.e("InternalFreeSpace : ", InternalFreeSpace);

        String InternalTotalSpace = convertBytes(getInternalTotalSpace());
        Log.e("InternalTotalSpace : ", InternalTotalSpace);

        String InternalUsedSpace = convertBytes(getInternalUsedSpace());
        Log.e("InternalUsedSpace : ", InternalUsedSpace);

    }

    private void getExternalStorage123() {

        StatFs internalStatFs = new StatFs( Environment.getRootDirectory().getAbsolutePath() );
        long internalTotal;
        long internalFree;

        StatFs externalStatFs = new StatFs( Environment.getExternalStorageDirectory().getAbsolutePath() );
        long externalTotal;
        long externalFree;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            internalTotal = ( internalStatFs.getBlockCountLong() * internalStatFs.getBlockSizeLong() ) / ( KILOBYTE * KILOBYTE );
            internalFree = ( internalStatFs.getAvailableBlocksLong() * internalStatFs.getBlockSizeLong() ) / ( KILOBYTE * KILOBYTE );
            externalTotal = ( externalStatFs.getBlockCountLong() * externalStatFs.getBlockSizeLong() ) / ( KILOBYTE * KILOBYTE );
            externalFree = ( externalStatFs.getAvailableBlocksLong() * externalStatFs.getBlockSizeLong() ) / ( KILOBYTE * KILOBYTE );
        }
        else {
            internalTotal = ( (long) internalStatFs.getBlockCount() * (long) internalStatFs.getBlockSize() ) / ( KILOBYTE * KILOBYTE );
            internalFree = ( (long) internalStatFs.getAvailableBlocks() * (long) internalStatFs.getBlockSize() ) / ( KILOBYTE * KILOBYTE );
            externalTotal = ( (long) externalStatFs.getBlockCount() * (long) externalStatFs.getBlockSize() ) / ( KILOBYTE * KILOBYTE );
            externalFree = ( (long) externalStatFs.getAvailableBlocks() * (long) externalStatFs.getBlockSize() ) / ( KILOBYTE * KILOBYTE );
        }

        long total = internalTotal + externalTotal;
        long free = internalFree + externalFree;
        long used = total - free;

        Log.e("total Space : ", String.valueOf(total));

        Log.e("free Space : ", String.valueOf(free));

        Log.e("Used Space : ", String.valueOf(used));

    }


    public long getInternalFreeSpace()    {
        //Get free Bytes...
        long bytesAvailable = stat1.getBlockSizeLong() * stat1.getAvailableBlocksLong();
        return bytesAvailable;
    }

    public long getInternalTotalSpace()    {
        //Get total Bytes
        long bytesTotal = (stat1.getBlockSizeLong() * stat1.getBlockCountLong());
        return bytesTotal;
    }

    public long getInternalUsedSpace()    {
        //Get used Bytes
        long bytesUsed = getInternalTotalSpace() - getInternalFreeSpace();
        return bytesUsed;
    }

    public String getInternalUsedSpace1()    {
        //Get used Bytes
        long bytesUsed = stat1.getTotalBytes()/(1024*1024*1024);
        return bytesUsed/1024+" GB";
    }

    public static String convertBytes (long size){
        long Kb = 1  * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size <  Kb)                 return floatForm(        size     ) + " byte";
        if (size >= Kb && size < Mb)    return floatForm((double)size / Kb) + " KB";
        if (size >= Mb && size < Gb)    return floatForm((double)size / Mb) + " MB";
        if (size >= Gb && size < Tb)    return floatForm((double)size / Gb) + " GB";
        if (size >= Tb && size < Pb)    return floatForm((double)size / Tb) + " TB";
        if (size >= Pb && size < Eb)    return floatForm((double)size / Pb) + " PB";
        if (size >= Eb)                 return floatForm((double)size / Eb) + " EB";

        return "anything...";
    }

    public static String floatForm (double d)    {
        return new DecimalFormat("#.##").format(d);
    }



    public void getExternalStorage() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } else {
            bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
        }
        long megAvailable = bytesAvailable / (1024 * 1024);
        Log.e("", "Available MB : " + megAvailable);

        txt_availableStorage.setText(String.format("%s", String.valueOf(getAvailableExternalMemorySize())));
        txt_totalStorage.setText(String.format("%s", String.valueOf(getTotalExternalMemorySize())));

        txt_availableInternalStorage.setText(String.format("%s", String.valueOf(getAvailableInternalMemorySize())));
        txt_totalInternalStorage.setText(String.format("%s", String.valueOf(getTotalInternalMemorySize())));

        txt_availableMemory.setText(String.format("%s MB", String.valueOf(megAvailable)));
        //      txt_totalMemory.setText(String.format("%s", String.valueOf(getTotalRAM())));
        txt_isSDCard.setText(String.format("%s", String.valueOf(isSdCardOnDevice(this))));

        txt_ReadCPUinfo.setText(ReadCPUinfo());

        double ram = getTotalRAM();
        if (ram > 0) {
            if (ram <= 0.5) {
                txt_totalMemory.setText(String.format("%s", String.valueOf("512 MB")));
            } else
                txt_totalMemory.setText(String.format("%.0f GB", Math.ceil(ram)));

        }

        Log.e("Storage test", "readableFileSize : " + readableFileSize());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.showStorageVolumes();
        }

        double totalSize = new File(getApplicationContext().getFilesDir().getAbsoluteFile().toString()).getTotalSpace();
        double totMb = totalSize / (1024 * 1024);
        Log.e("Storage test", "internal Total memory : " + totMb);


        double availableSize = new File(getApplicationContext().getFilesDir().getAbsoluteFile().toString()).getFreeSpace();
        double freeMb = availableSize / (1024 * 1024);
        Log.e("Storage test", "Internal free size : " + freeMb);

        long freeBytesExternal = new File(getExternalFilesDir(null).toString()).getFreeSpace();
        int free = (int) (freeBytesExternal / (1024 * 1024));
        long totalSize1 = new File(getExternalFilesDir(null).toString()).getTotalSpace();
        int total = (int) (totalSize1 / (1024 * 1024));
        String availableMb = free + "Mb out of " + total + "MB";
        Log.e("Storage test", availableMb);

    }

    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return formatSize(availableBlocks * blockSize);
    }

    public static String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return formatSize(totalBlocks * blockSize);
    }

    public static String getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return formatSize(availableBlocks * blockSize);
        } else {
            return "";
        }
    }

    public static String getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return formatSize(totalBlocks * blockSize);
        } else {
            return "";
        }
    }

    public String getRamSize() {
        ActivityManager actManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long totalMemory = memInfo.totalMem;
        //  long megAvailable = totalMemory / (1024 * 1024);
        return formatSize(totalMemory);
    }

    public static String formatSize(long size) {
        String suffix = null;
        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
                if (size >= 1024) {
                    suffix = "GB";
                    size /= 1024;
                }
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    public static boolean isSdCardOnDevice(Context context) {
        File[] storages = ContextCompat.getExternalFilesDirs(context, null);
        if (storages.length > 1 && storages[0] != null && storages[1] != null)
            return true;
        else
            return false;
    }

    public static String readableFileSize() {
        long availableSpace = -1L;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2)
            availableSpace = (long) stat.getBlockSizeLong() * (long) stat.getAvailableBlocksLong();
        else
            availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();

        if (availableSpace <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(availableSpace) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(availableSpace / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    private void showStorageVolumes() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            StorageStatsManager storageStatsManager = null;
            storageStatsManager = (StorageStatsManager) getSystemService(Context.STORAGE_STATS_SERVICE);

            StorageManager storageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
            if (storageManager == null || storageStatsManager == null) {
                return;
            }
            List<StorageVolume> storageVolumes = storageManager.getStorageVolumes();
            for (StorageVolume storageVolume : storageVolumes) {
                final String uuidStr = storageVolume.getUuid();
                try {

                    final UUID uuid = uuidStr == null ? StorageManager.UUID_DEFAULT : UUID.fromString(uuidStr);
                    Log.e("AppLog", "Android O Specific storage:" + uuid + " : " + storageVolume.getDescription(this) + " : " + storageVolume.getState());
                    Log.e("AppLog", "Android O Specific getFreeBytes:" + Formatter.formatShortFileSize(this, storageStatsManager.getFreeBytes(uuid)));
                    Log.e("AppLog", "Android O Specific getTotalBytes:" + Formatter.formatShortFileSize(this, storageStatsManager.getTotalBytes(uuid)));
                } catch (Exception e) {
                    // IGNORED
                    Log.e("Storage", "showStorageVolumes not working");
                }
            }
        }
    }

    /**
     * @return double RAM in GB
     */
    public double getTotalRAM() {

        RandomAccessFile reader = null;
        String load = null;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        double totRam = 0;
        String lastValue = "";
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();

            // Get the Number value from the string
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
                // System.out.println("Ram : " + value);
            }
            reader.close();

            totRam = Double.parseDouble(value);
            // totRam = totRam / 1024;

            return totRam / 1048576.0; // in gb

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Streams.close(reader);
        }
        return 0.0;
    }

    private String ReadCPUinfo() {
        ProcessBuilder cmd;
        StringBuffer strMemory = new StringBuffer();
        final ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager actvityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mInfo = new ActivityManager.MemoryInfo();
        actvityManager.getMemoryInfo(mInfo);
        strMemory.append("Available Memory : ");
        strMemory.append(mInfo.availMem);
        strMemory.append("\n");
        strMemory.append("\n");
        String result = strMemory.toString();
        try {
            String[] args = {"/system/bin/cat", "/proc/meminfo"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[1024];
            while (in.read(re) != -1) {
                System.out.println(new String(re));
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public void method1(){
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        //ITS show phone internal storage size
       // StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            // **ITS show phone SD card storage size**
         int blockSize=stat.getBlockSize();
         int availableBlocks=stat.getAvailableBlocks();
         Log.e("","statFs:"+"="+stat);
         Log.e("","SPACE AVAILABEL :"+"="+availableBlocks);

      //   occup_size_tv.setTextSize(availableBlocks);
      //   occupied_in_tv.setText(Integer.toString(availableBlocks));

         Log.e("","SIZE OCCUPY:"+"="+blockSize);

         //availabel_in_tv.setText((Formatter.formatFileSize(this,availableBlocks*blockSize)));
         Log.e("","FREE SPACE:="+Formatter.formatFileSize(this,availableBlocks*blockSize));
         Log.e("","FREE SPACE IN BYTES:"+availableBlocks+1042.f*1024.f);
         // bytesAvailable/(1024.f*1024.f);
         Log.e("","Test DATA : " + Formatter.formatShortFileSize(this,availableBlocks*blockSize));
    }


}
