package com.ventas.application.service;
import com.ventas.application.model.Producto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductoService {

    @Select("SELECT * FROM producto ORDER BY idproducto")
    List<Producto> findAll();

    @Update(
            "UPDATE producto SET cantidad=#{cantidad}, precio=#{precio}, tipo=#{tipo} WHERE idproducto=#{id}")
    void update(Producto producto);
}
