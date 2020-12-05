<template>
  <div>
    <Comment v-for="comment in comments" :user="getUser(comment)" :content="comment.text" :key="comment.id"/>
  </div>
</template>

<script>
import Comment from "@/components/middle/Posts/Comments/Comment/Comment";

export default {
  name: "Comments",
  props: {
    comments: {
      type: Array
    },
    users: {
      type: Array
    }
  },
  components: {
    Comment
  },
  methods: {
    getUser(comment) {
      return this.users[comment.userId].name
    }
  }
}
</script>

<style scoped>

</style>