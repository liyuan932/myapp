package com.mycompany.myapp.service;

import com.mycompany.myapp.common.exception.BizException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("bookService")
public class BookService {
    public static final String ADD_BOOK="insert into book(id,name) values(1,'duck-j2ee')";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addBook() throws Exception{
        this.jdbcTemplate.execute(ADD_BOOK);
        //throw new Exception("跳出执行");
        throw new BizException("跳出执行");
    }
}