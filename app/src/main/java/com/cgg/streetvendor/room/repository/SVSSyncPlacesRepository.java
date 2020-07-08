package com.cgg.streetvendor.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cgg.streetvendor.interfaces.DeleteVendingInterface;
import com.cgg.streetvendor.interfaces.GCCDivisionInterface;
import com.cgg.streetvendor.room.dao.SVSSyncPlacesDao;
import com.cgg.streetvendor.room.database.SVSDatabase;
import com.cgg.streetvendor.source.reposnse.places.DistrictEntity;
import com.cgg.streetvendor.source.reposnse.places.MandalEntity;
import com.cgg.streetvendor.source.reposnse.places.StateEntity;
import com.cgg.streetvendor.source.reposnse.places.VillageEntity;

import java.util.List;

public class SVSSyncPlacesRepository {
    private SVSSyncPlacesDao syncDao;
    private SVSDatabase db;

    public SVSSyncPlacesRepository(Application application) {
        db = SVSDatabase.getDatabase(application);
        syncDao = db.syncPlacesDao();
    }

    public LiveData<List<MandalEntity>> getDistrictMandals(String distId) {
        return syncDao.getDistrictMandals(distId);
    }

    public void deleteAllVendings(final DeleteVendingInterface vendingInterface) {
        new DeleteVendingAsyncTask(vendingInterface).execute();
    }


    public LiveData<List<VillageEntity>> getMandalVillages(String distId, String manId) {
        return syncDao.getMandalVillages(distId, manId);
    }

    public LiveData<List<StateEntity>> getAllstates() {
        return syncDao.getAllStates();
    }

    public LiveData<List<DistrictEntity>> getAllDistricts() {
        return syncDao.getAllDistricts();
    }


    public LiveData<List<MandalEntity>> getAllMandals() {
        return syncDao.getAllMandals();
    }

    public LiveData<List<VillageEntity>> getAllVillages() {
        return syncDao.getAllVillages();
    }

    public void insertStates(final GCCDivisionInterface dmvInterface, final List<StateEntity> stateEntities) {
        new InsertStateAsyncTask(dmvInterface, stateEntities).execute();
    }

    public void insertDistricts(final GCCDivisionInterface dmvInterface, final List<DistrictEntity> districtEntities) {
        new InsertDistrictAsyncTask(dmvInterface, districtEntities).execute();
    }

    public void insertMandals(final GCCDivisionInterface dmvInterface, final List<MandalEntity> mandalEntities) {
        new InsertMandalAsyncTask(dmvInterface, mandalEntities).execute();
    }

    public void insertVillages(final GCCDivisionInterface dmvInterface, final List<VillageEntity> villageEntities) {
        new InsertVillageAsyncTask(dmvInterface, villageEntities).execute();
    }


    @SuppressLint("StaticFieldLeak")
    private class DeleteVendingAsyncTask extends AsyncTask<Void, Void, Integer> {
        DeleteVendingInterface vendingInterface;
        DeleteVendingAsyncTask(DeleteVendingInterface vendingInterface){
            this.vendingInterface = vendingInterface;
        }
        @Override
        protected Integer doInBackground(Void... voids) {
            db.svsSyncVendingDao().deleteVenAddress();
            return db.svsSyncVendingDao().venAddressCount();
        }

        @Override
        protected void onPostExecute(Integer v) {
            super.onPostExecute(v);
            vendingInterface.venCount(v);
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class InsertStateAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<StateEntity> stateEntities;
        GCCDivisionInterface dmvInterface;

        InsertStateAsyncTask(GCCDivisionInterface dmvInterface,
                             List<StateEntity> stateEntities) {
            this.stateEntities = stateEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteStates();
            syncDao.insertState(stateEntities);
            return syncDao.stateCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.stateCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertDistrictAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<DistrictEntity> districtEntities;
        GCCDivisionInterface dmvInterface;

        InsertDistrictAsyncTask(GCCDivisionInterface dmvInterface,
                                List<DistrictEntity> districtEntities) {
            this.districtEntities = districtEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteDistricts();
            syncDao.insertDistricts(districtEntities);
            return syncDao.distCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.distCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertMandalAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<MandalEntity> mandalEntities;
        GCCDivisionInterface dmvInterface;

        InsertMandalAsyncTask(GCCDivisionInterface dmvInterface,
                              List<MandalEntity> mandalEntities) {
            this.mandalEntities = mandalEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteMandals();
            syncDao.insertMandals(mandalEntities);
            return syncDao.manCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.manCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertVillageAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<VillageEntity> villageEntities;
        GCCDivisionInterface dmvInterface;

        InsertVillageAsyncTask(GCCDivisionInterface dmvInterface,
                               List<VillageEntity> villageEntities) {
            this.villageEntities = villageEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteVillages();
            syncDao.insertVillages(villageEntities);
            return syncDao.vilCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.vilCount(integer);
        }
    }
//
//    @SuppressLint("StaticFieldLeak")
//    private class InsertDRGoDownAsyncTask extends AsyncTask<Void, Void, Integer> {
//        List<DrGodowns> DRGodowns;
//        GCCDivisionInterface dmvInterface;
//
//        InsertDRGoDownAsyncTask(GCCDivisionInterface dmvInterface,
//                                List<DrGodowns> DRGodowns) {
//            this.DRGodowns = DRGodowns;
//            this.dmvInterface = dmvInterface;
//        }
//
//        @Override
//        protected Integer doInBackground(Void... voids) {
//            syncDao.deleteDRGoDowns();
//            syncDao.insertDRGoDowns(DRGodowns);
//            return syncDao.drGoDownCount();
//        }
//
//        @Override
//        protected void onPostExecute(Integer integer) {
//            super.onPostExecute(integer);
//            dmvInterface.drGoDownCount(integer);
//        }
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private class InsertMFPGoDownAsyncTask extends AsyncTask<Void, Void, Integer> {
//        List<MFPGoDowns> mfpGoDowns;
//        GCCDivisionInterface dmvInterface;
//
//        InsertMFPGoDownAsyncTask(GCCDivisionInterface dmvInterface,
//                                 List<MFPGoDowns> mfpGoDowns) {
//            this.mfpGoDowns = mfpGoDowns;
//            this.dmvInterface = dmvInterface;
//        }
//
//        @Override
//        protected Integer doInBackground(Void... voids) {
//            syncDao.deleteMFPDowns();
//            syncDao.insertMFPGoDowns(mfpGoDowns);
//            return syncDao.mfpGoDownCount();
//        }
//
//        @Override
//        protected void onPostExecute(Integer integer) {
//            super.onPostExecute(integer);
//            dmvInterface.mfpGoDownCount(integer);
//        }
//    }
//
//
//    @SuppressLint("StaticFieldLeak")
//    private class InsertPUnitAsyncTask extends AsyncTask<Void, Void, Integer> {
//        List<PUnits> pUnits;
//        GCCDivisionInterface dmvInterface;
//
//        InsertPUnitAsyncTask(GCCDivisionInterface dmvInterface,
//                             List<PUnits> pUnits) {
//            this.pUnits = pUnits;
//            this.dmvInterface = dmvInterface;
//        }
//
//        @Override
//        protected Integer doInBackground(Void... voids) {
//            syncDao.deletePUnits();
//            syncDao.insertPUnits(pUnits);
//            return syncDao.pUnitCount();
//        }
//
//        @Override
//        protected void onPostExecute(Integer integer) {
//            super.onPostExecute(integer);
//            dmvInterface.pUNitCount(integer);
//        }
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private class InsertPetrolAsyncTask extends AsyncTask<Void, Void, Integer> {
//        List<PetrolSupplierInfo> petrolSupplierInfos;
//        GCCDivisionInterface dmvInterface;
//
//        InsertPetrolAsyncTask(GCCDivisionInterface dmvInterface,
//                              List<PetrolSupplierInfo> petrolSupplierInfos) {
//            this.petrolSupplierInfos = petrolSupplierInfos;
//            this.dmvInterface = dmvInterface;
//        }
//
//        @Override
//        protected Integer doInBackground(Void... voids) {
//            syncDao.deletePetrolPumps();
//            syncDao.insertPetrolPumps(petrolSupplierInfos);
//            return syncDao.petrolPumpCount();
//        }
//
//        @Override
//        protected void onPostExecute(Integer integer) {
//            super.onPostExecute(integer);
//            dmvInterface.petrolPumpCount(integer);
//        }
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private class InsertLPGAsyncTask extends AsyncTask<Void, Void, Integer> {
//        List<LPGSupplierInfo> lpgSupplierInfos;
//        GCCDivisionInterface dmvInterface;
//
//        InsertLPGAsyncTask(GCCDivisionInterface dmvInterface,
//                              List<LPGSupplierInfo> lpgSupplierInfos) {
//            this.lpgSupplierInfos = lpgSupplierInfos;
//            this.dmvInterface = dmvInterface;
//        }
//
//        @Override
//        protected Integer doInBackground(Void... voids) {
//            syncDao.deleteLPG();
//            syncDao.insertLPG(lpgSupplierInfos);
//            return syncDao.lpgCount();
//        }
//
//        @Override
//        protected void onPostExecute(Integer integer) {
//            super.onPostExecute(integer);
//            dmvInterface.lpgCount(integer);
//        }
//    }
}
