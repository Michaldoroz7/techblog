import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { Comment } from "../types/Comment.type";

interface CommentState {
  comments: Comment[];
  loading: boolean;
  error?: string;
}

const initialState: CommentState = {
  comments: [],
  loading: false,
};

export const fetchCommentsByIds = createAsyncThunk(
  "comments/fetchByIds",
  async (ids: number[], thunkAPI) => {
    try {

      const token = localStorage.getItem("token");

      const response = await axios.post(
        "http://localhost:8080/comment-service/api/comments/article/by-ids",
        { ids }, 
        { headers: {
          Authorization:  `Bearer ${token}`
        }}
      );
      return response.data as Comment[];
    } catch (err: any) {
      return thunkAPI.rejectWithValue(
        err.response?.data?.message || "Nie udało się pobrać komentarzy"
      );
    }
  }
);

export const createComment = createAsyncThunk(
  'comments/create',
  async (
    payload: { articleId: number; content: string },
    { getState, rejectWithValue }
  ) => {
    try {
    
      const token = localStorage.getItem("token");

      const response = await axios.post(
        "http://localhost:8080/comment-service/api/comments",
        {
          articleId: payload.articleId,
          content: payload.content,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      return response.data as Comment;
    } catch (err: any) {
      return rejectWithValue(err.response?.data || 'Błąd podczas tworzenia komentarza');
    }
  }
);

export const deleteComment = createAsyncThunk(
  "comments/delete",
  async (commentId: number, thunkAPI) => {
    try {
      const token = localStorage.getItem("token");
      await axios.delete(
        `http://localhost:8080/comment-service/api/comments/${commentId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      return commentId;
    } catch (err: any) {
      return thunkAPI.rejectWithValue(
        err.response?.data?.message || "Nie udało się usunąć komentarza"
      );
    }
  }
);

const commentSlice = createSlice({
  name: "comments",
  initialState,
  reducers: {
    clearComments: (state) => {
      state.comments = [];
      state.error = undefined;
      state.loading = false;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchCommentsByIds.pending, (state) => {
        state.loading = true;
        state.error = undefined;
      })
      .addCase(fetchCommentsByIds.fulfilled, (state, action) => {
        state.loading = false;
        state.comments = action.payload;
      })
      .addCase(fetchCommentsByIds.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      .addCase(createComment.pending, (state) => {
        state.loading = true;
        state.error = undefined;
      })
      .addCase(createComment.fulfilled, (state, action) => {
        state.loading = false;
        state.comments.push(action.payload);
      })
      .addCase(createComment.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export const { clearComments } = commentSlice.actions;
export default commentSlice.reducer;
