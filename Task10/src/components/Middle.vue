<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="sortPosts" :users="users" :commentCounts="getCommentCounts()"/>
            <Enter v-if="page === 'Enter'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <Post v-if="page === 'Post'" :post="post" :users="users" :show-comments="true" :comments="postComments" :comment-count="postComments.length"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "@/components/sidebar/Sidebar";
import Index from "@/components/middle/Index";
import Enter from "@/components/middle/Enter";
import WritePost from "@/components/middle/WritePost";
import EditPost from "@/components/middle/EditPost";
import Register from "@/components/middle/Register";
import Users from "@/components/middle/Users/Users";
import Post from "@/components/middle/Posts/Post";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
            post: null,
            postComments: null
        }
    },
    components: {
        WritePost,
        Enter,
        Index,
        Sidebar,
        EditPost,
        Register,
        Users,
        Post
    },
    methods: {
      getCommentCounts: function () {
        let idToCommentCount = new Object();
        Object.values(this.comments).forEach(comment => {
          if (idToCommentCount[comment.postId] === undefined) {
            idToCommentCount[comment.postId] = 1;
          } else {
            idToCommentCount[comment.postId]++;
          }
        })
        return idToCommentCount;
      }
    },
    props: ["posts", "users", "comments"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        },
        sortPosts: function () {
          return Object.values(this.posts).sort((a, b) => b.id - a.id);
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
        this.$root.$on("showPost", (post) => {
          this.post = post;
          this.postComments = Object.values(this.comments).filter(c => c.postId === post.id);
          this.page = "Post";
        })
    }
}
</script>

<style scoped>

</style>