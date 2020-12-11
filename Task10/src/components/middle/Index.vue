<template>
  <div>
    <Post v-for="post in posts" :post="post" :users="users" :commentCount="commentCounts[post.id]" :key="post.id"/>
  </div>
</template>

<script>
import Post from "@/components/middle/Posts/Post";

export default {
  name: "Index",
  props: ["posts", "commentCounts", "users"],
  components: {
    Post
  }
}
</script>

<style scoped>

</style>