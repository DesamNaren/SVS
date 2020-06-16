package com.cgg.streetvendor.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

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

    public SVSSyncPlacesRepository(Application application) {
        SVSDatabase db = SVSDatabase.getDatabase(application);
        syncDao = db.syncPlacesDao();
    }

    public LiveData<List<MandalEntity>> getDistrictMandals(String distId) {
        return syncDao.getDistrictMandals(distId);
    }

    public LiveData<List<VillageEntity>> getMandalVillages(String distId, String manId) {
        return syncDao.getMandalVillages(distId, manId);
    }

    public LiveData<String> getStateId(String stateName) {
        return syncDao.getStateId(stateName);
    }

    public LiveData<String> getTelStateId(String stateName) {
        return syncDao.getTelStateId(stateName);
    }

    public LiveData<String> getDistrictID(String distName) {
        return syncDao.getDistId(distName);
    }

    public LiveData<String> getTelDistId(String distName) {
        return syncDao.getTelDistId(distName);
    }

    public LiveData<String> getMandalID(String manName, String distID) {
        return syncDao.getMandalId(manName, distID);
    }

    public LiveData<String> getTelMandalId(String manName, String distID) {
        return syncDao.getTelMandalId(manName, distID);
    }

    public LiveData<String> getVillageId(String vilName, String distID, String manID) {
        return syncDao.getVillageId(vilName, distID, manID);
    }    public LiveData<String> getTelVillageId(String vilName, String distID, String manID) {
        return syncDao.getTelVillageId(vilName, distID, manID);
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


//    public void insertDRGoDowns(final GCCDivisionInterface dmvInterface, final List<DrGodowns> DRGoDowns) {
//        new InsertDRGoDownAsyncTask(dmvInterface, DRGoDowns).execute();
//    }
//
//    public void insertMFPGoDowns(final GCCDivisionInterface dmvInterface, final List<MFPGoDowns> mfpGoDowns) {
//        new InsertMFPGoDownAsyncTask(dmvInterface, mfpGoDowns).execute();
//    }
//
//    public void insertPUnits(final GCCDivisionInterface dmvInterface, final List<PUnits> pUnits) {
//        new InsertPUnitAsyncTask(dmvInterface, pUnits).execute();
//    }
//
//    public void insertPetrolPumps(final GCCDivisionInterface dmvInterface, final List<PetrolSupplierInfo> petrolSupplierInfos) {
//        new InsertPetrolAsyncTask(dmvInterface, petrolSupplierInfos).execute();
//    }
//
//    public void insertLpg(final GCCDivisionInterface dmvInterface, final List<LPGSupplierInfo> lpgSupplierInfos) {
//        new InsertLPGAsyncTask(dmvInterface, lpgSupplierInfos).execute();
//    }


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
