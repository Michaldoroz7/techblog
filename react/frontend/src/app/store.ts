import { configureStore } from '@reduxjs/toolkit';
import authSlice from '../features/auth/authSlice';
import userSlice from '../features/auth/userSlice';
import articleSlice from '../features/articleSlice';
import commentSlice from '../features/commentSlice';
import statisticsSlice from '../features/statisticsSlice';

export const store = configureStore({
  reducer: {
    auth: authSlice,
    user: userSlice,
    article: articleSlice,
    comment: commentSlice,
    statistics: statisticsSlice,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
