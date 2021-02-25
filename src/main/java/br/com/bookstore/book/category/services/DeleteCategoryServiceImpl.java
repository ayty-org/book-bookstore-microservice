package br.com.bookstore.book.category.services;

import br.com.bookstore.book.category.CategoryRepository;
import br.com.bookstore.book.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteCategoryServiceImpl implements DeleteCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }

        categoryRepository.deleteById(id);
    }
}
