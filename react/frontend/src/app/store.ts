import { configureStore } from '@reduxjs/toolkit';
import authSlice from '../features/auth/authSlice';
import quoteSlice from '../features/quoteSlice';
import userSlice from '../features/auth/userSlice';
import articleSlice from '../features/articleSlice';
import commentSlice from '../features/commentSlice';

export const store = configureStore({
  reducer: {
    auth: authSlice,
    quote: quoteSlice,
    user: userSlice,
    article: articleSlice,
    comment: commentSlice
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
