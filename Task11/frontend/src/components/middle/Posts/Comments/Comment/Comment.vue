<template>
  <div>{{ user }} : {{ content }}</div>
</template>

<script>
export default {
  name: "Comment",
  props: {
    content: {
      type: String
    },
    user: {
      type: String
    }
  }
}
</script>

<style scoped>

</style>