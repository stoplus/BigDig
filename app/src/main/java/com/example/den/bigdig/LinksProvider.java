package com.example.den.bigdig;

/**
 * Created by den on 26.02.2018.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

public class LinksProvider extends ContentProvider{

    private SqliteDatabaseManager dbManager;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ContractLinks.AUTHORITY, ContractLinks.PATH_LINKS_DATA, 1);
        sUriMatcher.addURI(ContractLinks.AUTHORITY, ContractLinks.PATH_LINKS_DATA + "/#", 2);
        sUriMatcher.addURI(ContractLinks.AUTHORITY, ContractLinks.PATH_STATUS_VALUE, 3);
        sUriMatcher.addURI(ContractLinks.AUTHORITY, ContractLinks.PATH_STATUS_VALUE + "/#", 4);
    }

    @Override
    public boolean onCreate() {
        dbManager = new SqliteDatabaseManager(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projections, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        Cursor mCursor = null;

        switch (sUriMatcher.match(uri)){
            case 1:
                mCursor = db.query(ContractLinks.Links.TABLE_NAME_LINK_DATA, projections, selection, selectionArgs, null, null, null);
                break;
            case 2:
                selection = selection + ContractLinks.Links.ID + " = " + uri.getLastPathSegment();
                mCursor = db.query(ContractLinks.Links.TABLE_NAME_LINK_DATA, projections, selection, selectionArgs, null, null, null);
                break;
            case 3:
                mCursor = db.query(ContractLinks.Links.TABLE_NAME_STATUS, projections, selection, selectionArgs, null, null, null);
                break;
            case 4:
                selection = selection + ContractLinks.Links.ID + " = " + uri.getLastPathSegment();
                mCursor = db.query(ContractLinks.Links.TABLE_NAME_STATUS, projections, selection, selectionArgs, null, null, null);
                break;
            default:
                Toast.makeText(getContext(), "Invalid content uri", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
        mCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return mCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case 1:
                return ContractLinks.CONTENT_DICTIONARY_LIST;
            case 2:
                return ContractLinks.CONTENT_DICTIONARY_ITEM;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = dbManager.getWritableDatabase();

        if(sUriMatcher.match(uri) != 1) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        long rowId = db.insert(ContractLinks.Links.TABLE_NAME_LINK_DATA, null, contentValues);
        if(rowId > 0) {
            Uri articleUri = ContentUris.withAppendedId(ContractLinks.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(articleUri, null);
            return articleUri;
        }
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        int count = 0;
        switch(sUriMatcher.match(uri)) {
            case 1:
                count = db.delete(ContractLinks.Links.TABLE_NAME_LINK_DATA, selection, selectionArgs);
                break;
            case 2:
                String rowId = uri.getPathSegments().get(1);
                count = db.delete(ContractLinks.Links.TABLE_NAME_LINK_DATA, ContractLinks.Links.ID + " = " + rowId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        int count = 0;
        switch (sUriMatcher.match(uri)){
            case 1:
                count = db.update(ContractLinks.Links.TABLE_NAME_LINK_DATA, contentValues, selection, selectionArgs);
                break;

            case 2:
                String rowId = uri.getPathSegments().get(1);
                count = db.update(ContractLinks.Links.TABLE_NAME_LINK_DATA, contentValues, ContractLinks.Links.ID + " = " + rowId +
                        (!TextUtils.isEmpty(selection) ? " AND (" + ")" : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
