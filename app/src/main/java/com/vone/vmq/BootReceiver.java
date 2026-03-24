package com.vone.vmq;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 开机自启动接收器
 * 用于在设备重启后自动重新启动通知监听服务
 */
public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(TAG, "设备开机，尝试重启监听服务");
            
            try {
                // 重启通知监听服务
                toggleNotificationListenerService(context);
                
                Toast.makeText(context, "V免签监控服务已启动", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(TAG, "启动服务失败: " + e.getMessage(), e);
            }
        }
    }

    /**
     * 重启通知监听服务
     */
    private void toggleNotificationListenerService(Context context) {
        PackageManager pm = context.getPackageManager();
        
        // 先禁用
        pm.setComponentEnabledSetting(
            new ComponentName(context, NeNotificationService2.class),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        );

        // 再启用
        pm.setComponentEnabledSetting(
            new ComponentName(context, NeNotificationService2.class),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        );
        
        Log.d(TAG, "通知监听服务已重启");
    }
}

