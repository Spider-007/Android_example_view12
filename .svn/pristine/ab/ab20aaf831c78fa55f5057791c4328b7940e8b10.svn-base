package com.htmitech.commonx.base.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

public class DialogFactory {

	public static ProgressDialog creatProgressDialog(Context context) {
		return new ProgressDialog(context);
	}

	public static ProgressDialog creatProgressDialog(Context context,
			String title, String mes) {
		ProgressDialog md = creatProgressDialog(context);
		md.setTitle(title);
		md.setMessage(mes);
		md.setCancelable(true);
		return md;
	}

	public static ProgressDialog creatProgressDialog(Context context,
			int title, String mes) {
		return creatProgressDialog(context, getString(context, title), mes);

	}

	public static ProgressDialog creatProgressDialog(Context context,
			int title, int mes) {
		return creatProgressDialog(context, getString(context, title), context
				.getResources().getString(mes));

	}

	public static void addProgressDialogButton(ProgressDialog dialog,
			String bname, int buttonid) {
		dialog.setButton(buttonid, bname, getOnClickListener());
	}

	public static void addProgressDialogButton(ProgressDialog dialog,
			int bname, int buttonid) {
		dialog.setButton(buttonid, getString(dialog.getContext(), bname),
				getOnClickListener());
	}

	public static void addProgressDialogButtons(ProgressDialog dialog,
			int[] buttonid, String[] bname,
			DialogInterface.OnClickListener listener) {
		if (buttonid == null) {
			return;
		}
		for (int i = 0; i < buttonid.length; i++) {
			dialog.setButton(buttonid[i], bname[i], listener);

		}
	}

	public static void addProgressDialogButtons(ProgressDialog dialog,
			int[] buttonid, int[] bname,
			DialogInterface.OnClickListener listener) {
		if (buttonid == null)
			return;
		for (int i = 0; i < buttonid.length; i++) {
			dialog.setButton(buttonid[i],
					getString(dialog.getContext(), bname[i]), listener);
		}
	}

	public static void addProgressDialogButtons(ProgressDialog dialog,
			int[] buttonid, String[] bname,
			DialogInterface.OnClickListener[] listeners) {
		if (buttonid == null || listeners == null) {
			return;
		}
		for (int i = 0; i < buttonid.length; i++) {
			dialog.setButton(buttonid[i], bname[i], listeners[i]);
		}
	}

	public static void addProgressDialogButtons(ProgressDialog dialog,
			int[] buttonid, int[] bname,
			DialogInterface.OnClickListener[] listeners) {
		if (buttonid == null || listeners == null) {
			return;
		}
		for (int i = 0; i < buttonid.length; i++) {
			dialog.setButton(buttonid[i],
					getString(dialog.getContext(), bname[i]), listeners[i]);
		}
	}

	public static Dialog creatDialog(Context context, int title, int mes) {
		return new AlertDialog.Builder(context)
				.setTitle(getString(context, title))
				.setMessage(getString(context, mes)).setCancelable(false)
				.create();
	}

	public static Dialog creatDialog(Context context, int title, String mes) {
		return new AlertDialog.Builder(context)
				.setTitle(getString(context, title)).setMessage(mes)
				.setCancelable(false).create();
	}

	public static Dialog creatDialog(Context context, String title, String mes) {
		return new AlertDialog.Builder(context).setTitle(title).setMessage(mes)
				.setCancelable(false).create();
	}

	public static Dialog creatDialog(Context context, String title, int mes) {
		return new AlertDialog.Builder(context).setTitle(title)
				.setMessage(getString(context, mes)).setCancelable(false)
				.create();
	}

	public static Dialog creatDialog(Context context, int title, String mes,
			View edittext) {
		return new AlertDialog.Builder(context)
				.setTitle(getString(context, title)).setMessage(mes)
				.setView(edittext).setCancelable(false).create();
	}

	public static void addDialogButton(Dialog dialog, int buttonid, int bname) {
		((AlertDialog) dialog).setButton(buttonid,
				getString(dialog.getContext(), bname), getOnClickListener());
	}

	public static void addDialogButton(Dialog dialog, int buttonid, String bname) {
		((AlertDialog) dialog).setButton(buttonid, bname, getOnClickListener());
	}

	public static void addDialogButton(Dialog dialog, int buttonid, int bname,
			DialogInterface.OnClickListener listeners) {
		((AlertDialog) dialog).setButton(buttonid,
				getString(dialog.getContext(), bname), listeners);
	}

	public static void addDialogButton(Dialog dialog, int buttonid,
			String bname, DialogInterface.OnClickListener listeners) {
		((AlertDialog) dialog).setButton(buttonid, bname, listeners);
	}

	public static void addDialogButtons(Dialog dialog, int[] buttonid,
			String[] bname, DialogInterface.OnClickListener listeners) {
		if (buttonid == null)
			return;
		for (int i = 0; i < bname.length; i++) {
			((AlertDialog) dialog).setButton(buttonid[i], bname[i], listeners);
		}
	}

	public static void addDialogButtons(Dialog dialog, int[] buttonid,
			int[] bname, DialogInterface.OnClickListener listeners) {
		if (buttonid == null)
			return;
		for (int i = 0; i < bname.length; i++) {
			((AlertDialog) dialog).setButton(buttonid[i],
					getString(dialog.getContext(), bname[i]), listeners);
		}
	}

	public static String getString(Context context, int strid) {
		return context.getResources().getString(strid);
	}

	private static DialogInterface.OnClickListener getOnClickListener() {

		return new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		};
	}

	public static void showToast(Context context, String message, int duration) {
		Toast.makeText(context, message, duration).show();
	}

	public static void showToast(Context context, int messageid, int duration) {
		Toast.makeText(context, messageid, duration).show();
	}

	public static void showToastLong(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static void showToasShort(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void showToastLong(Context context, int messageid) {
		Toast.makeText(context, messageid, Toast.LENGTH_LONG).show();
	}

	public static void showToasShort(Context context, int messageid) {
		Toast.makeText(context, messageid, Toast.LENGTH_SHORT).show();
	}

	/*public static void showDateDialog(Context context,
			DateDialogInterFace listener, int Year, int Month, int Day) {
		PickerDialogListener ls = new PickerDialogListener();
		ls.setDateInterface(listener);
		DatePickerDialog dateDialog = new DatePickerDialog(context,
				ls.onDateSetListener, Year, Month, Day);
		dateDialog.show();
	}

	public static void showTimeDialog(Context context,
			TimeDialogInterFace listener, int Hour, int Minute,
			boolean is24HourView) {
		PickerDialogListener ls = new PickerDialogListener();
		ls.setTimeInterface(listener);
		TimePickerDialog timeDialog = new TimePickerDialog(context,
				ls.onTimeSetListener, Hour, Minute, is24HourView);
		timeDialog.show();
	}*/

}
