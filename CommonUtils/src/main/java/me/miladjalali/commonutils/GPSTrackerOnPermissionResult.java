package me.miladjalali.commonutils;

interface GPSTrackerOnPermissionResult {
    void onRequestPermissionsResult(int requestCode,
                                    String[] permissions, int[] grantResults);
}
