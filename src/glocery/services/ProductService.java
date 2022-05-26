package glocery.services;

import glocery.models.Product;
import glocery.tools.FileFormat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ProductService implements IProductService {
    public final static String path = "data/products.csv";
    private static ProductService instance;

    private ProductService() {
    }

    public static ProductService getInstance() {
        if (instance == null)
            instance = new ProductService();
        return instance;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        List<String> records = FileFormat.read(path);
        for (String record : records) {
            productList.add(Product.parse(record));
        }
        return productList;
    }

    @Override
    public void add(Product newProduct) {
        List<Product> productList = findAll();
        newProduct.setProCreatedTime(Instant.now());
        productList.add(newProduct);
        FileFormat.write(path, productList);
    }

    @Override
    public void update(Product newProduct) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (newProduct.getProId().equals(product.getProId())) {
                String proName = newProduct.getProName();
                if (proName != null && !proName.isEmpty()) {
                    product.setProName(proName);
                }
                Integer proQuantity = newProduct.getProQuantity();
                if (proQuantity != null) {
                    product.setProQuantity(proQuantity);
                }
                Double proPrice = newProduct.getProPrice();
                if (proPrice != null) {
                    product.setProPrice(proPrice);
                }
                product.setProUpdatedTime(Instant.now());
                FileFormat.write(path, productList);
                break;
            }
        }
    }

    @Override
    public Product findById(Long id) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (product.getProId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public boolean exist(Long id) {
        return findById(id) != null;
    }

//    @Override
//    public boolean existByName(String name) {
//        List<Product> productList = findAll();
//        for (Product product : productList) {
//            if (name.equals(product.getProName())) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public boolean existsById(Long id) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (id.equals(product.getProId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteById(Long id) {
        List<Product> productList = findAll();
        productList.removeIf(new Predicate<Product>() {
            @Override
            public boolean test(Product product) {
                return id.equals(product.getProId());
            }
        });
        FileFormat.write(path, productList);
    }

    @Override
    public List<Product> sortProductByPriceASC() {
        List<Product> productList = new ArrayList<>(findAll());
        productList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                double result = o1.getProPrice() - o2.getProPrice();
                if (result == 0) {
                    return 0;
                }
                return result > 0 ? 1 : -1;
            }
        });
        return productList;
    }

    @Override
    public List<Product> sortProductByPriceDES() {
        List<Product> productList = new ArrayList<>(findAll());
        productList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                double result = o2.getProPrice() - o1.getProPrice();
                if (result == 0) {
                    return 0;
                }
                return result > 0 ? 1 : -1;
            }
        });
        return productList;
    }
}
