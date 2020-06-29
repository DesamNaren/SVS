package com.cgg.streetvendor.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cgg.streetvendor.interfaces.GCCDivisionInterface;
import com.cgg.streetvendor.room.dao.SVSSyncBBDao;
import com.cgg.streetvendor.room.database.SVSDatabase;
import com.cgg.streetvendor.source.reposnse.bankbranch.BankEntity;
import com.cgg.streetvendor.source.reposnse.bankbranch.BranchEntity;

import java.util.List;

public class SVSSyncBBRepository {
    private SVSSyncBBDao bbDao;

    public SVSSyncBBRepository(Application application) {
        SVSDatabase db = SVSDatabase.getDatabase(application);
        bbDao = db.syncBBDao();
    }

    public LiveData<List<BranchEntity>> getBankBranches(String bankId) {
        return bbDao.getBankBranches(bankId);
    }

    public LiveData<String> getIFSCCode(String bankId, String branchId) {
        return bbDao.getIFSCCode(bankId, branchId);
    }

    public LiveData<String> getBankID(String bankName, int pos) {
        return bbDao.getBankId(bankName, pos);
    }

    public LiveData<String> getTelBankId(String bankName, int pos) {
        return bbDao.getTelBankId(bankName,pos);
    }


    public LiveData<String> getBranchId(String branchName, String bankId) {
        return bbDao.getBranchId(branchName, bankId);
    }

    public LiveData<String> getTelBranchId(String branchName, String bankId) {
        return bbDao.getTelBranchId(branchName, bankId);
    }

    public LiveData<List<BankEntity>> getAllBanks() {
        return bbDao.getAllBanks();
    }

    public LiveData<List<BranchEntity>> getAllBranches() {
        return bbDao.getAllBranches();
    }

    public void insertBanks(final GCCDivisionInterface dmvInterface, final List<BankEntity> bankEntities) {
        new InsertBankAsyncTask(dmvInterface, bankEntities).execute();
    }

    public void insertBranches(final GCCDivisionInterface dmvInterface, final List<BranchEntity> branchEntities) {
        new InsertBranchAsyncTask(dmvInterface, branchEntities).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertBankAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<BankEntity> bankEntities;
        GCCDivisionInterface dmvInterface;

        InsertBankAsyncTask(GCCDivisionInterface dmvInterface,
                            List<BankEntity> bankEntities) {
            this.bankEntities = bankEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            bbDao.clearPrimaryKey();
            bbDao.deleteBanks();
            bbDao.insertBanks(bankEntities);
            return bbDao.bankCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.bankCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertBranchAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<BranchEntity> branchEntities;
        GCCDivisionInterface dmvInterface;

        InsertBranchAsyncTask(GCCDivisionInterface dmvInterface,
                              List<BranchEntity> branchEntities) {
            this.branchEntities = branchEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            bbDao.deleteBranches();
            bbDao.insertBranches(branchEntities);
            return bbDao.branchCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.branchCount(integer);
        }
    }

}
