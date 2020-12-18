<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="sortPosts"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <WritePost v-if="page === 'WritePost'"/>
            <Post v-if="page === 'Post'" :post="post" :show-add-comment-field="user != null" :show-comments="true" :key="post.id"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "@/components/sidebar/Sidebar";
import Index from "@/components/middle/Index";
import Enter from "@/components/middle/Enter";
import Register from "@/components/middle/Register";
import Users from "@/components/middle/Users/Users";
import WritePost from "@/components/middle/WritePost";
import Post from "@/components/middle/Posts/Post";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
            post: null
        }
    },
    components: {
        Register,
        Enter,
        Index,
        Sidebar,
        Users,
        WritePost,
        Post
    },
    methods: {
      // getCommentCounts: function () {
      //   let idToCommentCount = new Object();
      //   Object.values(this.comments).forEach(comment => {
      //     if (idToCommentCount[comment.postId] === undefined) {
      //       idToCommentCount[comment.postId] = 1;
      //     } else {
      //       idToCommentCount[comment.postId]++;
      //     }
      //   })
      //   return idToCommentCount;
      // }
    },
    props: ["posts", "users", "user"],
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
          this.page = "Post";
        })
    }
}
</script>

<style scoped>

</style>