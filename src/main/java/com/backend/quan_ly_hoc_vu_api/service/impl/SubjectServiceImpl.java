package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.service.SubjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
}
