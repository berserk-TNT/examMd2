package glocery.services;

import glocery.models.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();

    void add(Product newProduct);

    void update(Product newProduct);

    Product findById(Long id);

    boolean exist(Long id);

    //boolean existByName(String name);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Product> sortProductByPriceASC();

    List<Product> sortProductByPriceDES();
}
