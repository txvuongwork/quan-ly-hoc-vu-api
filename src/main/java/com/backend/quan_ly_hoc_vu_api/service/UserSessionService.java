package com.backend.quan_ly_hoc_vu_api.service;

public interface UserSessionService {

    boolean checkUserSession(String tokenId);
    void removeExpiredSession(String tokenId);

}
