package com.ddwu.notalonemarket.mapper;

import com.ddwu.notalonemarket.domain.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostMapper {
	void insertPost(Post post);
    Post selectPostById(Long id);
    List<Post> selectAllSellingPosts();

    List<Post> selectPostsByKeyword(String keyword);
    List<Post> selectPostsByCategory(String category);
    List<Post> selectPostsByWriterId(Long writerId);    
    List<Post> selectPostsByCategoryId(Long categoryId);
    List<Post> searchByKeywordAndCategory(Map<String, Object> param);
}