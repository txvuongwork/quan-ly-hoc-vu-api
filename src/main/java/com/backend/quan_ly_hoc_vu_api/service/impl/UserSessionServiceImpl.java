package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.repository.UserSessionRepository;
import com.backend.quan_ly_hoc_vu_api.service.UserSessionService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSessionServiceImpl implements UserSessionService {

    private final UserSessionRepository userSessionRepo;

    @Override
    public boolean checkUserSession(String tokenId) {
        return userSessionRepo.existsByTokenId(tokenId);
    }

    @Override
    public void removeExpiredSession(String tokenId) {
        userSessionRepo.findByTokenId(tokenId).ifPresent(userSessionRepo::delete);
    }

}
