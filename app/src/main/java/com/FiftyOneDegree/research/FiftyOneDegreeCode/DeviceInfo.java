package com.FiftyOneDegree.research.FiftyOneDegreeCode;

import com.google.gson.annotations.SerializedName;

public class DeviceInfo {
    @SerializedName("packageName")
    private String packageName;
    @SerializedName("imeiNumber")
    private String imeiNumber;
    @SerializedName("tacCode")
    private String tacCode;
    @SerializedName("manufacturer")
    private String manufacturer;
    @SerializedName("model")
    private String model;
    @SerializedName("version")
    private Integer version;
    @SerializedName("screenResolution")
    private String screenResolution;
    @SerializedName("dpi")
    private Integer dpi;
    @SerializedName("serialNumber")
    private String serialNumber;
    @SerializedName("board")
    private String board;
    @SerializedName("brand")
    private String brand;
    @SerializedName("deviceName")
    private String deviceName;
    @SerializedName("displayBuildId")
    private String displayBuildId;
    @SerializedName("fingerprint")
    private String fingerprint;
    @SerializedName("hardware")
    private String hardware;
    @SerializedName("host")
    private String host;
    @SerializedName("lableId")
    private String lableId;
    @SerializedName("product")
    private String product;
    @SerializedName("radioFirmwareNumber")
    private String radioFirmwareNumber;
    @SerializedName("buildTags")
    private String buildTags;
    @SerializedName("times")
    private Long times;
    @SerializedName("buildType")
    private String buildType;
    @SerializedName("user")
    private String user;
    @SerializedName("userAgentSystem")
    private String userAgentSystem;
    @SerializedName("userAgentWebView")
    private String userAgentWebView;
    @SerializedName("currentBatteryCharge")
    private String currentBatteryCharge;
    @SerializedName("backCameraMegaPixels")
    private Float backCameraMegaPixels;
    @SerializedName("backCameraMegaPixelsOriginal")
    private Float backCameraMegaPixelsOriginal;
    @SerializedName("cameraTypes")
    private String cameraTypes;
    @SerializedName("frontCameraMegaPixels")
    private float frontCameraMegaPixels;
    @SerializedName("frontCameraMegaPixelsOriginal")
    private float frontCameraMegaPixelsOriginal;
    @SerializedName("hasCamera")
    private boolean hasCamera;
    @SerializedName("hasNFC")
    private boolean hasNFC;
    @SerializedName("hasFingerPrint")
    private boolean hasFingerPrint;
    @SerializedName("hasBluetooth")
    private boolean hasBluetooth;
    @SerializedName("supportsPhoneCalls")
    private boolean supportsPhoneCalls;
    @SerializedName("deviceType")
    private String deviceType;
    @SerializedName("isMobile")
    private boolean isMobile;
    @SerializedName("isSmallScreen")
    private boolean isSmallScreen;
    @SerializedName("isSmartPhone")
    private boolean isSmartPhone;
    @SerializedName("isTablet")
    private boolean isTablet;
    @SerializedName("deviceRAM")
    private long deviceRAM;
    @SerializedName("maxInternalStorage")
    private String maxInternalStorage;
    @SerializedName("hardwareFamily")
    private String hardwareFamily;
    @SerializedName("hardwareModel")
    private String hardwareModel;
    @SerializedName("hardwareModelVariants")
    private String hardwareModelVariants;
    @SerializedName("hardwareName")
    private String hardwareName;
    @SerializedName("hardwareVendor")
    private String hardwareVendor;
    @SerializedName("oem")
    private String oem;
    @SerializedName("nativeBrand")
    private String nativeBrand;
    @SerializedName("nativeDevice")
    private String nativeDevice;
    @SerializedName("nativeModel")
    private String nativeModel;
    @SerializedName("cpu")
    private String cpu;
    @SerializedName("cpuCores")
    private int cpuCores;
    @SerializedName("cpuDesigner")
    private String cpuDesigner;
    @SerializedName("cpuMaximumFrequencyOriginal")
    private String cpuMaximumFrequencyOriginal;
    @SerializedName("cpuMaximumFrequency")
    private String cpuMaximumFrequency;
    @SerializedName("soC")
    private String soC;
    @SerializedName("screenInchesDiagonal")
    private double screenInchesDiagonal;
    @SerializedName("ScreenInchesDiagonalOriginal")
    private double ScreenInchesDiagonalOriginal;
    @SerializedName("screenInchesDiagonalRounded")
    private int screenInchesDiagonalRounded;
    @SerializedName("screenInchesHeight")
    private double screenInchesHeight;
    @SerializedName("screenInchesSquare")
    private int screenInchesSquare;
    @SerializedName("screenInchesWidth")
    private double screenInchesWidth;
    @SerializedName("screenMMDiagonal")
    private double screenMMDiagonal;
    @SerializedName("screenMMDiagonalRounded")
    private int screenMMDiagonalRounded;
    @SerializedName("screenMMHeight")
    private double screenMMHeight;
    @SerializedName("screenMMSquare")
    private int screenMMSquare;
    @SerializedName("screenMMWidth")
    private double screenMMWidth;
    @SerializedName("screenPixelsHeight")
    private int screenPixelsHeight;
    @SerializedName("screenPixelsWidth")
    private int screenPixelsWidth;
    @SerializedName("supportedSensorTypes")
    private String supportedSensorTypes;
    @SerializedName("maxNumberOfSIMCards")
    private int maxNumberOfSIMCards;
    @SerializedName("onPowerConsumption")
    private int onPowerConsumption;
    @SerializedName("refreshRate")
    private int refreshRate;
    @SerializedName("publicIP")
    private String publicIP;
    @SerializedName("mccNetwork")
    private int mccNetwork;
    @SerializedName("mncNetwork")
    private int mncNetwork;
    @SerializedName("mccSIM")
    private int mccSIM;
    @SerializedName("mncSIM")
    private int mncSIM;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("connectivityType")
    private String connectivityType;
    @SerializedName("networkType")
    private String networkType;

    public DeviceInfo() {
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getImeiNumber() {
        return imeiNumber;
    }

    public void setImeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    public Integer getDpi() {
        return dpi;
    }

    public void setDpi(Integer dpi) {
        this.dpi = dpi;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDisplayBuildId() {
        return displayBuildId;
    }

    public void setDisplayBuildId(String displayBuildId) {
        this.displayBuildId = displayBuildId;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLableId() {
        return lableId;
    }

    public void setLableId(String lableId) {
        this.lableId = lableId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getRadioFirmwareNumber() {
        return radioFirmwareNumber;
    }

    public void setRadioFirmwareNumber(String radioFirmwareNumber) {
        this.radioFirmwareNumber = radioFirmwareNumber;
    }

    public String getBuildTags() {
        return buildTags;
    }

    public void setBuildTags(String buildTags) {
        this.buildTags = buildTags;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserAgentSystem() {
        return userAgentSystem;
    }

    public void setUserAgentSystem(String userAgentSystem) {
        this.userAgentSystem = userAgentSystem;
    }

    public String getUserAgentWebView() {
        return userAgentWebView;
    }

    public void setUserAgentWebView(String userAgentWebView) {
        this.userAgentWebView = userAgentWebView;
    }

    public String getCurrentBatteryCharge() {
        return currentBatteryCharge;
    }

    public void setCurrentBatteryCharge(String currentBatteryCharge) {
        this.currentBatteryCharge = currentBatteryCharge;
    }

    public Float getBackCameraMegaPixels() {
        return backCameraMegaPixels;
    }

    public void setBackCameraMegaPixels(Float backCameraMegaPixels) {
        this.backCameraMegaPixels = backCameraMegaPixels;
    }

    public Float getBackCameraMegaPixelsOriginal() {
        return backCameraMegaPixelsOriginal;
    }

    public void setBackCameraMegaPixelsOriginal(Float backCameraMegaPixelsOriginal) {
        this.backCameraMegaPixelsOriginal = backCameraMegaPixelsOriginal;
    }

    public String getCameraTypes() {
        return cameraTypes;
    }

    public void setCameraTypes(String cameraTypes) {
        this.cameraTypes = cameraTypes;
    }

    public float getFrontCameraMegaPixels() {
        return frontCameraMegaPixels;
    }

    public void setFrontCameraMegaPixels(float frontCameraMegaPixels) {
        this.frontCameraMegaPixels = frontCameraMegaPixels;
    }

    public float getFrontCameraMegaPixelsOriginal() {
        return frontCameraMegaPixelsOriginal;
    }

    public void setFrontCameraMegaPixelsOriginal(float frontCameraMegaPixelsOriginal) {
        this.frontCameraMegaPixelsOriginal = frontCameraMegaPixelsOriginal;
    }

    public boolean getHasCamera() {
        return hasCamera;
    }

    public void setHasCamera(boolean hasCamera) {
        this.hasCamera = hasCamera;
    }

    public boolean getHasNFC() {
        return hasNFC;
    }

    public void setHasNFC(boolean hasNFC) {
        this.hasNFC = hasNFC;
    }

    public boolean isHasFingerPrint() {
        return hasFingerPrint;
    }

    public void setHasFingerPrint(boolean hasFingerPrint) {
        this.hasFingerPrint = hasFingerPrint;
    }

    public boolean isHasBluetooth() {
        return hasBluetooth;
    }

    public void setHasBluetooth(boolean hasBluetooth) {
        this.hasBluetooth = hasBluetooth;
    }

    public boolean getSupportsPhoneCalls() {
        return supportsPhoneCalls;
    }

    public void setSupportsPhoneCalls(boolean supportsPhoneCalls) {
        this.supportsPhoneCalls = supportsPhoneCalls;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public boolean getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(boolean isMobile) {
        this.isMobile = isMobile;
    }

    public boolean getIsSmallScreen() {
        return isSmallScreen;
    }

    public void setIsSmallScreen(boolean isSmallScreen) {
        this.isSmallScreen = isSmallScreen;
    }

    public boolean getIsSmartPhone() {
        return isSmartPhone;
    }

    public void setIsSmartPhone(boolean isSmartPhone) {
        this.isSmartPhone = isSmartPhone;
    }

    public boolean getIsTablet() {
        return isTablet;
    }

    public void setIsTablet(boolean isTablet) {
        this.isTablet = isTablet;
    }

    public long getDeviceRAM() {
        return deviceRAM;
    }

    public void setDeviceRAM(long deviceRAM) {
        this.deviceRAM = deviceRAM;
    }

    public String getMaxInternalStorage() {
        return maxInternalStorage;
    }

    public void setMaxInternalStorage(String maxInternalStorage) {
        this.maxInternalStorage = maxInternalStorage;
    }

    public String getHardwareFamily() {
        return hardwareFamily;
    }

    public void setHardwareFamily(String hardwareFamily) {
        this.hardwareFamily = hardwareFamily;
    }

    public String getHardwareModel() {
        return hardwareModel;
    }

    public void setHardwareModel(String hardwareModel) {
        this.hardwareModel = hardwareModel;
    }

    public String getHardwareModelVariants() {
        return hardwareModelVariants;
    }

    public void setHardwareModelVariants(String hardwareModelVariants) {
        this.hardwareModelVariants = hardwareModelVariants;
    }

    public String getHardwareName() {
        return hardwareName;
    }

    public void setHardwareName(String hardwareName) {
        this.hardwareName = hardwareName;
    }

    public String getHardwareVendor() {
        return hardwareVendor;
    }

    public void setHardwareVendor(String hardwareVendor) {
        this.hardwareVendor = hardwareVendor;
    }

    public String getOem() {
        return oem;
    }

    public void setOem(String oem) {
        this.oem = oem;
    }

    public String getNativeBrand() {
        return nativeBrand;
    }

    public void setNativeBrand(String nativeBrand) {
        this.nativeBrand = nativeBrand;
    }

    public String getNativeDevice() {
        return nativeDevice;
    }

    public void setNativeDevice(String nativeDevice) {
        this.nativeDevice = nativeDevice;
    }

    public String getNativeModel() {
        return nativeModel;
    }

    public void setNativeModel(String nativeModel) {
        this.nativeModel = nativeModel;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public int getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(int cpuCores) {
        this.cpuCores = cpuCores;
    }

    public String getCpuDesigner() {
        return cpuDesigner;
    }

    public void setCpuDesigner(String cpuDesigner) {
        this.cpuDesigner = cpuDesigner;
    }

    public String getCpuMaximumFrequencyOriginal() {
        return cpuMaximumFrequencyOriginal;
    }

    public void setCpuMaximumFrequencyOriginal(String cpuMaximumFrequencyOriginal) {
        this.cpuMaximumFrequencyOriginal = cpuMaximumFrequencyOriginal;
    }

    public String getCpuMaximumFrequency() {
        return cpuMaximumFrequency;
    }

    public void setCpuMaximumFrequency(String cpuMaximumFrequency) {
        this.cpuMaximumFrequency = cpuMaximumFrequency;
    }

    public String getSoC() {
        return soC;
    }

    public void setSoC(String soC) {
        this.soC = soC;
    }

    public double getScreenInchesDiagonal() {
        return screenInchesDiagonal;
    }

    public void setScreenInchesDiagonal(double screenInchesDiagonal) {
        this.screenInchesDiagonal = screenInchesDiagonal;
    }

    public double getScreenInchesDiagonalOriginal() {
        return ScreenInchesDiagonalOriginal;
    }

    public void setScreenInchesDiagonalOriginal(double screenInchesDiagonalOriginal) {
        ScreenInchesDiagonalOriginal = screenInchesDiagonalOriginal;
    }

    public int getScreenInchesDiagonalRounded() {
        return screenInchesDiagonalRounded;
    }

    public void setScreenInchesDiagonalRounded(int screenInchesDiagonalRounded) {
        this.screenInchesDiagonalRounded = screenInchesDiagonalRounded;
    }

    public double getScreenInchesHeight() {
        return screenInchesHeight;
    }

    public void setScreenInchesHeight(double screenInchesHeight) {
        this.screenInchesHeight = screenInchesHeight;
    }

    public int getScreenInchesSquare() {
        return screenInchesSquare;
    }

    public void setScreenInchesSquare(int screenInchesSquare) {
        this.screenInchesSquare = screenInchesSquare;
    }

    public double getScreenInchesWidth() {
        return screenInchesWidth;
    }

    public void setScreenInchesWidth(double screenInchesWidth) {
        this.screenInchesWidth = screenInchesWidth;
    }

    public double getScreenMMDiagonal() {
        return screenMMDiagonal;
    }

    public void setScreenMMDiagonal(double screenMMDiagonal) {
        this.screenMMDiagonal = screenMMDiagonal;
    }

    public int getScreenMMDiagonalRounded() {
        return screenMMDiagonalRounded;
    }

    public void setScreenMMDiagonalRounded(int screenMMDiagonalRounded) {
        this.screenMMDiagonalRounded = screenMMDiagonalRounded;
    }

    public double getScreenMMHeight() {
        return screenMMHeight;
    }

    public void setScreenMMHeight(double screenMMHeight) {
        this.screenMMHeight = screenMMHeight;
    }

    public int getScreenMMSquare() {
        return screenMMSquare;
    }

    public void setScreenMMSquare(int screenMMSquare) {
        this.screenMMSquare = screenMMSquare;
    }

    public double getScreenMMWidth() {
        return screenMMWidth;
    }

    public void setScreenMMWidth(double screenMMWidth) {
        this.screenMMWidth = screenMMWidth;
    }

    public int getScreenPixelsHeight() {
        return screenPixelsHeight;
    }

    public void setScreenPixelsHeight(int screenPixelsHeight) {
        this.screenPixelsHeight = screenPixelsHeight;
    }

    public int getScreenPixelsWidth() {
        return screenPixelsWidth;
    }

    public void setScreenPixelsWidth(int screenPixelsWidth) {
        this.screenPixelsWidth = screenPixelsWidth;
    }

    public String getSupportedSensorTypes() {
        return supportedSensorTypes;
    }

    public void setSupportedSensorTypes(String supportedSensorTypes) {
        this.supportedSensorTypes = supportedSensorTypes;
    }

    public int getMaxNumberOfSIMCards() {
        return maxNumberOfSIMCards;
    }

    public void setMaxNumberOfSIMCards(int maxNumberOfSIMCards) {
        this.maxNumberOfSIMCards = maxNumberOfSIMCards;
    }

    public int getOnPowerConsumption() {
        return onPowerConsumption;
    }

    public void setOnPowerConsumption(int onPowerConsumption) {
        this.onPowerConsumption = onPowerConsumption;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public String getPublicIP() {
        return publicIP;
    }

    public void setPublicIP(String publicIP) {
        this.publicIP = publicIP;
    }

    public int getMccNetwork() {
        return mccNetwork;
    }

    public void setMccNetwork(int mccNetwork) {
        this.mccNetwork = mccNetwork;
    }

    public int getMncNetwork() {
        return mncNetwork;
    }

    public void setMncNetwork(int mncNetwork) {
        this.mncNetwork = mncNetwork;
    }

    public int getMccSIM() {
        return mccSIM;
    }

    public void setMccSIM(int mccSIM) {
        this.mccSIM = mccSIM;
    }

    public int getMncSIM() {
        return mncSIM;
    }

    public void setMncSIM(int mncSIM) {
        this.mncSIM = mncSIM;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTacCode() {
        return tacCode;
    }

    public void setTacCode(String tacCode) {
        this.tacCode = tacCode;
    }

    public String getConnectivityType() {
        return connectivityType;
    }

    public void setConnectivityType(String connectivityType) {
        this.connectivityType = connectivityType;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }
}

