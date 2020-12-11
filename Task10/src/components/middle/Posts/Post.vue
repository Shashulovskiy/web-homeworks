<template>
  <div>
    <article>
      <a href="#" @click.prevent='showPost()'>
        <div class="title">{{ post.title }}</div>
      </a>
      <div class="information">By {{ users[post.userId].login }}</div>
      <div class="body">{{ post.text }}</div>
      <ul class="attachment">
        <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
        <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
      </ul>
      <div class="footer">
        <div class="left">
          <img src="../../../assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
          <span class="positive-score">+173</span>
          <img src="../../../assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
        </div>
        <div class="right">
          <img src="../../../assets/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
          A while ago
          <img src="../../../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
          <a href="#">{{ commentCount === undefined ? 0 : commentCount }}</a>
        </div>
      </div>
    </article>
    <Comments v-if="showComments" :comments="comments" :users="users"/>
  </div>
</template>

<script>
import Comments from "@/components/middle/Posts/Comments/Comments";

export default {
  name: "Post",
  components: {Comments},
  props: {
    post: {
      type: Object
    },
    users: {
      type: Object
    },
    commentCount: {
      type: Number
    },
    showComments: {
      type: Boolean,
      default: false
    },
    comments: {
      type: Array
    }
  },
  methods: {
    showPost() {
      this.$root.$emit("showPost", this.post);
    }
  }
}
</script>

<style scoped>
.information {
  padding: 0.25rem 0;
}
</style>