export default function auth({ next, router }) {
  // signup 페이지는 인증 체크에서 제외
  if (router.currentRoute.value.path === '/signup') {
    return next()
  }
  
  const isLoggedIn = localStorage.getItem('token')
  
  if (!isLoggedIn) {
    return next('/login')
  }
  
  return next()
} 