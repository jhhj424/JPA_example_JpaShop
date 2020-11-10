package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;
    @Autowired EntityManager em;

    @Test
    public void 상품저장() throws Exception {
        //given
        Book book = new Book();
        book.setAuthor("지토");
        book.setName("스프링 JPA");
        book.setPrice(10000);
        book.setStockQuantity(100);

        //when
        itemService.saveItem(book); // 저장

        //then
        assertEquals(book, itemService.findOne((long) 1));
    }

    @Test
    public void 이미존재하는상품은merge한다() throws Exception {
        //given
        Book book = new Book();
        book.setAuthor("지토");
        book.setName("스프링 JPA");
        book.setPrice(10000);
        book.setStockQuantity(100);

        itemService.saveItem(book); // 저장

        //when
        book.removeStock(1);
        itemService.saveItem(book); // 저장

        //then
        assertEquals(book.getStockQuantity(), itemService.findOne((long) 1).getStockQuantity());
    }
}