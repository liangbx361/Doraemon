import com.doraemon.test.BaseTest;
import com.doraemon.wish.practice.dao.model.PracticeAuthor;
import com.doraemon.wish.practice.dao.model.PracticeBook;
import com.doraemon.wish.practice.dao.repository.PracticeAuthorRepository;
import com.doraemon.wish.practice.dao.repository.PracticeBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * https://juejin.im/post/6844904154561773575
 */
public class ManyToManyTest extends BaseTest {

    @Autowired
    private PracticeAuthorRepository authorRepository;
    @Autowired
    private PracticeBookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    public void test() {
        // 创建
        PracticeBook book = new PracticeBook();
        book.setName("代码整洁之道");

        PracticeAuthor author = new PracticeAuthor();
        author.setName("乔治·R·R·马丁");

        book.getAuthors().add(author);
        author.getBooks().add(book);

        book = bookRepository.save(book);
        author = authorRepository.save(author);

        // 更新
        book = bookRepository.findById(book.getId()).get();
        book.setName("代码整洁之道第二版");
        bookRepository.save(book);

        bookRepository.delete(book);

//        // 从Book中移除Author
//        author = authorRepository.findById(author.getId()).get();
//        book.getAuthors().remove(0);
//        bookRepository.save(book);
//        authorRepository.deleteById(author.getId());
    }
}
