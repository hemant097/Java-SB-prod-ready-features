package com.example.week4.postapplication.Service;

import com.example.week4.postapplication.DTO.PostDTO;
import com.example.week4.postapplication.Entities.Post;
import com.example.week4.postapplication.Exceptions.ResourceNotFound;
import com.example.week4.postapplication.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {


    private final ModelMapper modelMapper;
    private final PostRepository postRepo;

    @Override
    public PostDTO createPost(PostDTO inputPost) {

        Post post = modelMapper.map(inputPost, Post.class);

        System.out.println(post.getTitle());

        return modelMapper.map(postRepo.save(post), PostDTO.class);


    }

    @Override
    public List<PostDTO> getAllPosts() {
       List<Post> posts = postRepo.findAll();

      return posts
              .stream()
              .map(post->modelMapper.map(post, PostDTO.class))
              .collect(Collectors.toList());
    }

    @Override
    public PostDTO findPostById(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFound("post not found with id "+postId));

        return modelMapper.map(post,PostDTO.class);
    }

    @Override
    public PostDTO updatePost(Long postId, PostDTO input) {
        Post olderPost = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFound("post not found with id "+postId));

        input.setPostId(postId);
        modelMapper.map(input,olderPost);

        Post updatedPost = postRepo.save(olderPost);

        return modelMapper.map(updatedPost, PostDTO.class);


    }
}
