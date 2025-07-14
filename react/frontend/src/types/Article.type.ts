import { Comment } from "./Comment.type";

export interface Article {
  title: string;
  summary: string;
  content: string;
  category: string; 
  authorName: string;
  createdAt: string;
  comments: Comment[];
}