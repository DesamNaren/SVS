package com.cgg.streetvendor.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cgg.streetvendor.interfaces.GCCDivisionInterface;
import com.cgg.streetvendor.room.dao.SVSSyncKYCDao;
import com.cgg.streetvendor.room.database.SVSDatabase;
import com.cgg.streetvendor.source.reposnse.kyc.CasteEntity;
import com.cgg.streetvendor.source.reposnse.kyc.GenderEntity;
import com.cgg.streetvendor.source.reposnse.kyc.PWDEntity;
import com.cgg.streetvendor.source.reposnse.kyc.QualificationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.RelationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.ReligionEntity;

import java.util.List;

public class SVSSyncKYCRepository {
    private SVSSyncKYCDao kycDao;

    public SVSSyncKYCRepository(Application application) {
        SVSDatabase db = SVSDatabase.getDatabase(application);
        kycDao = db.syncKYCDao();
    }

    public LiveData<List<ReligionEntity>> getAllReligions() {
        return kycDao.getAllReligions();
    }

    public LiveData<List<GenderEntity>> getAllGenders() {
        return kycDao.getAllGenders();
    }

    public LiveData<List<CasteEntity>> getAllCaste() {
        return kycDao.getAllCaste();
    }


    public LiveData<List<PWDEntity>> getAllPWD() {
        return kycDao.getAllPWDs();
    }

    public LiveData<List<QualificationEntity>> getAllQual() {
        return kycDao.getAllQualification();
    }


    public LiveData<List<RelationEntity>> getAllRel() {
        return kycDao.getAllRelations();
    }

    public void insertReligion(final GCCDivisionInterface dmvInterface, final List<ReligionEntity> religionEntities) {
        new InsertReligionAsyncTask(dmvInterface, religionEntities).execute();
    }

    public void insertGender(final GCCDivisionInterface dmvInterface, final List<GenderEntity> genderEntities) {
        new InsertGenderAsyncTask(dmvInterface, genderEntities).execute();
    }

    public void insertCaste(final GCCDivisionInterface dmvInterface, final List<CasteEntity> casteEntities) {
        new InsertCasteAsyncTask(dmvInterface, casteEntities).execute();
    }

    public void insertPWD(final GCCDivisionInterface dmvInterface, final List<PWDEntity> pwdEntities) {
        new InsertPWDAsyncTask(dmvInterface, pwdEntities).execute();
    }


    public void insertQualification(final GCCDivisionInterface dmvInterface, final List<QualificationEntity> qualificationEntities) {
        new InsertQuaAsyncTask(dmvInterface, qualificationEntities).execute();
    }


    public void insertRelation(final GCCDivisionInterface dmvInterface, final List<RelationEntity> relationEntities) {
        new InsertRelationAsyncTask(dmvInterface, relationEntities).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertReligionAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<ReligionEntity> religionEntities;
        GCCDivisionInterface dmvInterface;

        InsertReligionAsyncTask(GCCDivisionInterface dmvInterface,
                                List<ReligionEntity> religionEntities) {
            this.religionEntities = religionEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            kycDao.deleteReligion();
            kycDao.insertReligions(religionEntities);
            return kycDao.religionCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.religionCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertGenderAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<GenderEntity> genderEntities;
        GCCDivisionInterface dmvInterface;

        InsertGenderAsyncTask(GCCDivisionInterface dmvInterface,
                              List<GenderEntity> genderEntities) {
            this.genderEntities = genderEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            kycDao.deleteGenders();
            kycDao.insertGenders(genderEntities);
            return kycDao.genCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.genderCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertCasteAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<CasteEntity> casteEntities;
        GCCDivisionInterface dmvInterface;

        InsertCasteAsyncTask(GCCDivisionInterface dmvInterface,
                             List<CasteEntity> casteEntities) {
            this.casteEntities = casteEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            kycDao.deleteCaste();
            kycDao.insertCaste(casteEntities);
            return kycDao.casteCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.casteCount(integer);
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class InsertPWDAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<PWDEntity> pwdEntities;
        GCCDivisionInterface dmvInterface;

        InsertPWDAsyncTask(GCCDivisionInterface dmvInterface,
                           List<PWDEntity> pwdEntities) {
            this.pwdEntities = pwdEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            kycDao.deletePWD();
            kycDao.insertPWD(pwdEntities);
            return kycDao.pwdCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.pwdCount(integer);
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class InsertQuaAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<QualificationEntity> qualificationEntities;
        GCCDivisionInterface dmvInterface;

        InsertQuaAsyncTask(GCCDivisionInterface dmvInterface,
                           List<QualificationEntity> qualificationEntities) {
            this.qualificationEntities = qualificationEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            kycDao.deleteQualification();
            kycDao.insertQualification(qualificationEntities);
            return kycDao.qualificationCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.quaCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertRelationAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<RelationEntity> relationEntities;
        GCCDivisionInterface dmvInterface;

        InsertRelationAsyncTask(GCCDivisionInterface dmvInterface,
                                List<RelationEntity> relationEntities) {
            this.relationEntities = relationEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            kycDao.deleteRelation();
            kycDao.insertRelation(relationEntities);
            return kycDao.relationCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.relCount(integer);
        }
    }

}
