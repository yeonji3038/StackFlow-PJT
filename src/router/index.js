const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/signup',
      name: 'Signup',
      component: Signup,
      meta: {
        requiresAuth: false  // signup 페이지는 인증 불필요로 설정
      }
    },
    // 다른 라우트들...
  ]
})

// 네비게이션 가드
router.beforeEach((to, from, next) => {
  const isAuthenticated = store.getters['auth/isAuthenticated']
  
  // signup 페이지는 인증 체크에서 제외
  if (to.path === '/signup') {
    next()
    return
  }
  
  if (to.matched.some(record => record.meta.requiresAuth) && !isAuthenticated) {
    next('/login')
  } else {
    next()
  }
}) 