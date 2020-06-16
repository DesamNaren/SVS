package com.cgg.streetvendor.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cgg.streetvendor.interfaces.GCCDivisionInterface;
import com.cgg.streetvendor.room.dao.SVSSyncBBDao;
import com.cgg.streetvendor.room.dao.SVSSyncULBDao;
import com.cgg.streetvendor.room.database.SVSDatabase;
import com.cgg.streetvendor.source.reposnse.bankbranch.BankEntity;
import com.cgg.streetvendor.source.reposnse.bankbranch.BranchEntity;
import com.cgg.streetvendor.source.reposnse.places.MandalEntity;
import com.cgg.streetvendor.source.reposnse.places.VillageEntity;
import com.cgg.streetvendor.source.reposnse.ulb.UlbEntity;
import com.cgg.streetvendor.source.reposnse.ulb.WardEntity;

import java.util.List;

public class SVSSyncULBRepository {
    private SVSSyncULBDao ulbDao;

    public SVSSyncULBRepository(Application application) {
        SVSDatabase db = SVSDatabase.getDatabase(application);
        ulbDao = db.syncULBDao();
    }

    public LiveData<List<UlbEntity>> getAllULBs() {
        return ulbDao.getAllULBs();
    }

    public LiveData<List<UlbEntity>> getLoggedULBs(String ulbId) {
        return ulbDao.getLoggedULBs(ulbId);
    }


    public LiveData<List<WardEntity>> getAllWards() {
        return ulbDao.getAllWards();
    }

    public LiveData<String> getULBID(String ulbName) {
        return ulbDao.getULBId(ulbName);
    }    public LiveData<String> getTelULBId(String ulbName) {
        return ulbDao.getTelULBId(ulbName);
    }

    public LiveData<String> getWardID(String wardName, String ulbId) {
        return ulbDao.getWardId(wardName, ulbId);
    }


    public LiveData<List<WardEntity>> getULBWards(String UlbId) {
        return ulbDao.getULBWards(UlbId);
    }

    public void insertULBs(final GCCDivisionInterface dmvInterface, final List<UlbEntity> ulbEntities) {
        new InsertULBAsyncTask(dmvInterface, ulbEntities).execute();
    }

    public void insertWards(final GCCDivisionInterface dmvInterface, final List<WardEntity> wardEntities) {
        new InsertWardAsyncTask(dmvInterface, wardEntities).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertULBAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<UlbEntity> ulbEntities;
        GCCDivisionInterface dmvInterface;

        InsertULBAsyncTask(GCCDivisionInterface dmvInterface,
                             List<UlbEntity> ulbEntities) {
            this.ulbEntities = ulbEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            ulbDao.deleteULBs();
            ulbDao.insertULBs(ulbEntities);
            return ulbDao.ulbCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.ulbCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertWardAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<WardEntity> wardEntities;
        GCCDivisionInterface dmvInterface;

        InsertWardAsyncTask(GCCDivisionInterface dmvInterface,
                                List<WardEntity> wardEntities) {
            this.wardEntities = wardEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            ulbDao.deleteWards();
            ulbDao.insertWards(wardEntities);
            return ulbDao.wardCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.wardCount(integer);
        }
    }

}
