//package org.softuni.workman.unit.validation;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.softuni.workman.domain.entities.Category;
//import org.softuni.workman.domain.entities.Product;
//import org.softuni.workman.domain.models.service.CategoryServiceModel;
//import org.softuni.workman.domain.models.service.ProductServiceModel;
//import org.softuni.workman.service.ProductService;
//import org.softuni.workman.validation.ProductValidationService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//public class ProductValidationServiceTests {
//    private ProductValidationService service;
//
//    @Before
//    public void setupTest() {
//        service = new ProductionValidationServiceImpl();
//    }
//
//    @Test
//    public void isValidWithProduct_whenValid_true() {
//        Product workman = new Product();
//        workman.setCategories(List.of(new Category()));
//        boolean result = service.isValid(workman);
//        assertTrue(result);
//    }
//
//    @Test
//    public void isValidWithProduct_whenNull_false() {
//        Product workman = null;
//        boolean result = service.isValid(workman);
//        assertFalse(result);
//    }
//
//    @Test
//    public void isValidWithProductServiceModel_whenNull_false() {
//        ProductServiceModel workman = null;
//        boolean result = service.isValid(workman);
//        assertFalse(result);
//    }
//
//    @Test
//    public void isValidWithProductServiceModel_whenValid_true() {
//        ProductServiceModel workman = new ProductServiceModel();
//        workman.setCategories(List.of(new CategoryServiceModel()));
//        boolean result = service.isValid(workman);
//        assertTrue(result);
//    }
//
//    @Test
//    public void t1() {
//        ProductServiceModel workman = new ProductServiceModel();
//        workman.setCategories(null);
//
//        boolean result = service.isValid(workman);
//        assertFalse(result);
//    }
//
//
//    @Test
//    public void t2() {
//        ProductServiceModel workman = new ProductServiceModel();
//        workman.setCategories(new ArrayList<>());
//
//        boolean result = service.isValid(workman);
//        assertFalse(result);
//    }
//}
