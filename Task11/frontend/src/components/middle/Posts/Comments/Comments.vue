<template>
  <div>
    <Comment v-for="comment in comments" :user="comment.user.login" :content="comment.text" :key="comment.id"/>
  </div>
</template>

<script>
import Comment from "@/components/middle/Posts/Comments/Comment/Comment";

export default {
  name: "Comments",
  props: {
    comments: {
      type: Array
    }
  },
  components: {
    Comment
  }
}
</script>

<style scoped>

</style>