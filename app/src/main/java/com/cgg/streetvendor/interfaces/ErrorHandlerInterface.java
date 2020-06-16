package com.cgg.streetvendor.interfaces;

import android.content.Context;

public interface ErrorHandlerInterface {
    void handleError(Throwable e, Context context);
}
