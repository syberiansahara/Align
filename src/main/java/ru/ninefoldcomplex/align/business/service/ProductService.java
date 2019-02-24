package ru.ninefoldcomplex.align.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ninefoldcomplex.align.business.dao.IDAO;

@Service
public class ProductService implements IService {
    @Autowired
    private IDAO dao;

}
