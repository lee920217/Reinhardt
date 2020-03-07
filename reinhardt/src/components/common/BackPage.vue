<template>
  <div class="back" v-on:touchstart="backStep"></div>
</template>

<script>
export default {
  name: "BackPage",
  props: {
    path: {
      type: String,
      default: "/"
    }
  },
  mounted() {},
  methods: {
    backStep() {
      const self = this;
      if (self.path === "user") {
        self.$emit("transfer", "login");
        return;
      } else if (self.path === "index") {
        self.$emit("transfer", "cancel");
        return;
      }
      self.$router.push(self.path ? self.path : "/");
    }
  }
};
</script>

<style scoped lang="scss">
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.back {
  position: fixed;
  top: px2rem(114);
  left: px2rem(36);
  width: px2rem(20);
  height: px2rem(36);
  background-image: url("../../assets/img/back.png");
  background-size: contain;
}
</style>
