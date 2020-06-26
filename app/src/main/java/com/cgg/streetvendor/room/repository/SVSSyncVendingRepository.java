package com.cgg.streetvendor.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cgg.streetvendor.interfaces.GCCDivisionInterface;
import com.cgg.streetvendor.room.dao.SVSSyncKYCDao;
import com.cgg.streetvendor.room.dao.SVSSyncVendingDao;
import com.cgg.streetvendor.room.database.SVSDatabase;
import com.cgg.streetvendor.source.reposnse.kyc.BusinessEntity;
import com.cgg.streetvendor.source.reposnse.kyc.CasteEntity;
import com.cgg.streetvendor.source.reposnse.kyc.GenderEntity;
import com.cgg.streetvendor.source.reposnse.kyc.PWDEntity;
import com.cgg.streetvendor.source.reposnse.kyc.QualificationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.RelationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.VendingAddressEntity;
import com.cgg.streetvendor.source.reposnse.kyc.VendingTypeEntity;

import java.util.List;

public class SVSSyncVendingRepository {
    private SVSSyncVendingDao vendingDao;

    public SVSSyncVendingRepository(Application application) {
        SVSDatabase db = SVSDatabase.getDatabase(application);
        vendingDao = db.svsSyncVendingDao();
    }

    public LiveData<String> getBusinessId(String business) {
        return vendingDao.getBusinessId(business);
    }

    public LiveData<String> getTelBusinessId(String business) {
        return vendingDao.getTelBusinessId(business);
    }

    public LiveData<String> getVenTypeId(String vType) {
        return vendingDao.getVenTypeId(vType);
    }

    public LiveData<String> getTelVenTypeId(String vType) {
        return vendingDao.getTelVenTypeId(vType);
    }

    public LiveData<String> getVenAddressId(String vAddress,String ULBId,String distId) {
        return vendingDao.getVenAddressId(vAddress, ULBId, distId);
    }

    public LiveData<String> getTelVenAddressId(String vAddress, String ULBId,String distId) {
        return vendingDao.getTelVenAddressId(vAddress, ULBId, distId);
    }

    public LiveData<List<BusinessEntity>> getAllBusiness() {
        return vendingDao.getAllBusiness();
    }

    public LiveData<List<VendingTypeEntity>> getAllVenTypes() {
        return vendingDao.getAllVenTypes();
    }


    public LiveData<List<VendingAddressEntity>> getAllVenAddress() {
        return vendingDao.getAllVenAddress();
    }

    public LiveData<List<VendingAddressEntity>> getAllVenAddress(String ulbId, String distId) {
        return vendingDao.getAllVenAddress(ulbId, distId);
    }



    public void insertBusiness(final GCCDivisionInterface dmvInterface, final List<BusinessEntity> businessEntities) {
        new InsertBusinessAsyncTask(dmvInterface, businessEntities).execute();
    }

    public void insertVenTypes(final GCCDivisionInterface dmvInterface, final List<VendingTypeEntity> vendingTypeEntities) {
        new InsertVenTypeAsyncTask(dmvInterface, vendingTypeEntities).execute();
    }

    public void insertVenAddress(final GCCDivisionInterface dmvInterface, final List<VendingAddressEntity> vendingAddressEntities) {
        new InsertVenAddressAsyncTask(dmvInterface, vendingAddressEntities).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertBusinessAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<BusinessEntity> businessEntities;
        GCCDivisionInterface dmvInterface;

        InsertBusinessAsyncTask(GCCDivisionInterface dmvInterface,
                             List<BusinessEntity> businessEntities) {
            this.businessEntities = businessEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            vendingDao.deleteBusiness();
            vendingDao.insertBusiness(businessEntities);
            return vendingDao.businessCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.businessCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertVenTypeAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<VendingTypeEntity> vendingTypeEntities;
        GCCDivisionInterface dmvInterface;

        InsertVenTypeAsyncTask(GCCDivisionInterface dmvInterface,
                                List<VendingTypeEntity> vendingTypeEntities) {
            this.vendingTypeEntities = vendingTypeEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            vendingDao.deleteVenType();
            vendingDao.insertVenType(vendingTypeEntities);
            return vendingDao.venTypeCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.venTypeCount(integer);
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class InsertVenAddressAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<VendingAddressEntity> vendingAddressEntities;
        GCCDivisionInterface dmvInterface;

        InsertVenAddressAsyncTask(GCCDivisionInterface dmvInterface,
                             List<VendingAddressEntity> vendingAddressEntities) {
            this.vendingAddressEntities = vendingAddressEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            vendingDao.deleteVenAddress();
            vendingDao.insertVenAddress(vendingAddressEntities);
            return vendingDao.venAddressCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.venAddCount(integer);
        }
    }

}
