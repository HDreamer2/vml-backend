package com.hku.vmlbackend.service;

public interface LinearRegressionService {
    void train(String[] features, String label, int epoch, String MD5);
    String getEpochData();


}
