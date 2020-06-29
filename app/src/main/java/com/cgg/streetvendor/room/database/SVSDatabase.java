package com.cgg.streetvendor.room.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cgg.streetvendor.room.dao.SVSSyncBBDao;
import com.cgg.streetvendor.room.dao.SVSSyncKYCDao;
import com.cgg.streetvendor.room.dao.SVSSyncPlacesDao;
import com.cgg.streetvendor.room.dao.SVSSyncULBDao;
import com.cgg.streetvendor.room.dao.SVSSyncVendingDao;
import com.cgg.streetvendor.source.reposnse.bankbranch.BankEntity;
import com.cgg.streetvendor.source.reposnse.bankbranch.BranchEntity;
import com.cgg.streetvendor.source.reposnse.kyc.BusinessEntity;
import com.cgg.streetvendor.source.reposnse.kyc.CasteEntity;
import com.cgg.streetvendor.source.reposnse.kyc.GenderEntity;
import com.cgg.streetvendor.source.reposnse.kyc.PWDEntity;
import com.cgg.streetvendor.source.reposnse.kyc.QualificationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.RelationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.ReligionEntity;
import com.cgg.streetvendor.source.reposnse.kyc.VendingAddressEntity;
import com.cgg.streetvendor.source.reposnse.kyc.VendingTypeEntity;
import com.cgg.streetvendor.source.reposnse.places.DistrictEntity;
import com.cgg.streetvendor.source.reposnse.places.MandalEntity;
import com.cgg.streetvendor.source.reposnse.places.StateEntity;
import com.cgg.streetvendor.source.reposnse.places.VillageEntity;
import com.cgg.streetvendor.source.reposnse.ulb.UlbEntity;
import com.cgg.streetvendor.source.reposnse.ulb.WardEntity;


/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {StateEntity.class, DistrictEntity.class, MandalEntity.class, VillageEntity.class,
        BankEntity.class, BranchEntity.class,
        UlbEntity.class, WardEntity.class,
        GenderEntity.class, CasteEntity.class, PWDEntity.class, QualificationEntity.class, RelationEntity.class,
        BusinessEntity.class, VendingTypeEntity.class, VendingAddressEntity.class
        , ReligionEntity.class},
        version = 2, exportSchema = false)
public abstract class SVSDatabase extends RoomDatabase {

    public abstract SVSSyncPlacesDao syncPlacesDao();

    public abstract SVSSyncBBDao syncBBDao();

    public abstract SVSSyncKYCDao syncKYCDao();

    public abstract SVSSyncVendingDao svsSyncVendingDao();

    public abstract SVSSyncULBDao syncULBDao();

    private static SVSDatabase INSTANCE;


    public static SVSDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SVSDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SVSDatabase.class, "SVS.db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
