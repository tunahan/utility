package com.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class WSLogMaskHelper
{
    /**
     * logStr
     *
     * @param logStr the body of the unmasked log to be logged
	 * @param serviceName the name of the unmasked log to be logged
     * @return masked string
     */
    public static StringBuffer maskLog(String logStr,String serviceName) throws Exception
    {
        HashMap maskCacheMap = null; // bind whatever cache holding service name or names you registered to get filtered
        if (maskCacheMap.containsKey(serviceName) && logStr != null && logStr.length() > 0)
        {
            ArrayList maskKeyArray = (ArrayList) maskCacheMap.get(serviceName);
            for (int i = 0; i < maskKeyArray.size(); i++)
            {
                WsLogMask wsLogMask = (WsLogMask) maskKeyArray.get(i);
                int firstIndex = logStr.indexOf("<" + wsLogMask.getMaskKey());
                firstIndex = logStr.indexOf(">",firstIndex);
                int secIndex = logStr.indexOf("</" + wsLogMask.getMaskKey() + ">", firstIndex);
                if (firstIndex > 0 && secIndex > 0)
                {
                    logStr = logStr.substring(0, firstIndex+1) + wsLogMask.getMaskedValue() + logStr.substring(secIndex);
                }
            }
        }
        return new StringBuffer(logStr); 
    }
}
