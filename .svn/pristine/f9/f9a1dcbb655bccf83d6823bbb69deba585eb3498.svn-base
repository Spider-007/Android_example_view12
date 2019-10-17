package com.htmitech_updown.updownloadmanagement.utils;

import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by htrf-pc on 2017/3/7.
 */
public class DBUnitl {

    //获取当前实体
    public static <T> T getObject(Class clazz, Cursor cursor) {
        T data = null;
        Constructor<?> constructor = findBestSuitConstructor(clazz);
        if (cursor.moveToFirst()) {

            try {
                data = (T) constructor
                        .newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            Field[] field = data.getClass().getDeclaredFields();
            for (int i = 0; i < field.length; i++) {
                Field f = field[i];
                f.setAccessible(true);
                Object val = null;
                String name = f.getName();
//                name = upperCase(name);
                try {
                    val = f.get(data);// 得到此属性的值
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                String type = f.getType().toString();// 得到此属性的类型
                Object value = new Object();
                if (type != null) {
                    try {
                        if (type.contains("String")) {
                            value = cursor.getString(cursor.getColumnIndex(name.toString()));
                            if (value == null) {
                                value = "";
                            }
                        } else if (type.equals("int")) {
                            value = cursor.getInt(cursor.getColumnIndex(name.toString()));
                        } else if (type.equals("double")) {
                            value = cursor.getDouble(cursor.getColumnIndex(name.toString()));
                        } else if (type.equals("float")) {
                            value = cursor.getFloat(cursor.getColumnIndex(name.toString()));
                        } else if (type.equals("boolean")) {
                            value = cursor.getInt(cursor.getColumnIndex(name.toString())) == 1 ? true : false;
                        } else if (type.equals("long")) {
                            value = cursor.getLong(cursor.getColumnIndex(name.toString()));
                        } else if (type.equals("short")) {
                            value = cursor.getShort(cursor.getColumnIndex(name.toString()));
                        } else {
                            continue;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                    try {
                        f.setAccessible(true);
                        f.set(data, value);
                    } catch (IllegalAccessException e) {
                        Log.e("data", e.toString());
                    }
                }
            }
        }
        cursor.close();
        return data;
    }

    //获取当前实体
    public static <T> T getObjects(Class clazz, Cursor cursor) {
        T data = null;
        Constructor<?> constructor = findBestSuitConstructor(clazz);
        try {
            data = (T) constructor
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Field[] field = data.getClass().getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            Field f = field[i];
            f.setAccessible(true);
            Object val = null;
            String name = f.getName();
//            name = upperCase(name);
            try {
                val = f.get(data);// 得到此属性的值
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            String type = f.getType().toString();// 得到此属性的类型
            Object value = new Object();
            if (type != null) {
                try {
                    if (type.contains("String")) {
                        value = cursor.getString(cursor.getColumnIndex(name.toString()));
                        if (value == null) {
                            value = "";
                        }
                    } else if (type.equals("int")) {
                        value = cursor.getInt(cursor.getColumnIndex(name.toString()));
                    } else if (type.equals("double")) {
                        value = cursor.getDouble(cursor.getColumnIndex(name.toString()));
                    } else if (type.equals("float")) {
                        value = cursor.getFloat(cursor.getColumnIndex(name.toString()));
                    } else if (type.equals("boolean")) {
                        value = cursor.getInt(cursor.getColumnIndex(name.toString())) == 1 ? true : false;
                    } else if (type.equals("long")) {
                        value = cursor.getLong(cursor.getColumnIndex(name.toString()));
                    } else if (type.equals("short")) {
                        value = cursor.getShort(cursor.getColumnIndex(name.toString()));
                    } else {
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
                try {
                    f.setAccessible(true);
                    f.set(data, value);
                } catch (IllegalAccessException e) {
                    Log.e("data", e.toString());
                }
            }
        }
        return data;
    }


    //获取当前实体的集合
    public static <T> List<T> getObjectList(Class clazz, Cursor cursor) {
        List<T> list = new ArrayList<T>();
        Constructor<?> constructor = findBestSuitConstructor(clazz);
        while (cursor.moveToNext()) {
            T data = null;
            try {
                data = (T) constructor
                        .newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            Field[] field = data.getClass().getDeclaredFields();
            for (int i = 0; i < field.length; i++) {
                Field f = field[i];
                f.setAccessible(true);
                Object val = null;
                String name = f.getName();
//                name = upperCase(name);
                try {
                    val = f.get(data);// 得到此属性的值
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                String type = f.getType().toString();// 得到此属性的类型
                Object value = new Object();
                if (type != null) {
                    try {
                        if (type.contains("String")) {
                            value = cursor.getString(cursor.getColumnIndex(name.toString()));
                            if (value == null) {
                                value = "";
                            }
                        } else if (type.equals("int")) {
                            value = cursor.getInt(cursor.getColumnIndex(name.toString()));
                        } else if (type.equals("double")) {
                            value = cursor.getDouble(cursor.getColumnIndex(name.toString()));
                        } else if (type.equals("float")) {
                            value = cursor.getFloat(cursor.getColumnIndex(name.toString()));
                        } else if (type.equals("boolean")) {
                            value = cursor.getInt(cursor.getColumnIndex(name.toString())) == 1 ? true : false;
                        } else if (type.equals("long")) {
                            value = cursor.getLong(cursor.getColumnIndex(name.toString()));
                        } else if (type.equals("short")) {
                            value = cursor.getShort(cursor.getColumnIndex(name.toString()));
                        } else {
                            continue;
                        }
                    } catch (Exception e) {
                        continue;
                    }

                    try {
                        f.setAccessible(true);
                        f.set(data, value);
                    } catch (IllegalAccessException e) {
                        Log.e("data", e.toString());
                    }
                }
            }
            list.add(data);
        }
        cursor.close();
        return list;
    }


    //判断是否是大写的
    private static boolean uppserOrLower(String value) {
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    private static String upperCase(String value) {
        if (uppserOrLower(value)) {
            return value;
        } else {
            return value.toUpperCase();
        }
    }

    private static Constructor<?> findBestSuitConstructor(Class<?> modelClass) {
        Constructor<?> finalConstructor = null;
        Constructor<?>[] constructors = modelClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (finalConstructor == null) {
                finalConstructor = constructor;
            } else {
                int finalParamLength = finalConstructor.getParameterTypes().length;
                int newParamLength = constructor.getParameterTypes().length;
                if (newParamLength < finalParamLength) {
                    finalConstructor = constructor;
                }
            }
        }
        finalConstructor.setAccessible(true);
        return finalConstructor;
    }
}
