package com.badlogic.androidgames.framework;

import java.io.IOException;
import java.io.InputStream;

import android.content.ContentValues;

public interface FileIO {
	public boolean CreateDBandTable(String sql);

	public boolean writeFile(ContentValues val);

	public String[][] readFile(String[] columns, String where, String[] value,
			String older, int quantity);
	
	public InputStream readSound(String fileName)throws IOException;
}
