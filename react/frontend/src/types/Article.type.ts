import { Comment } from "./Comment.type";

export interface Article {
  title: string;
  summary: string;
  content: string;
  category: string; 
  author: string;
  createdAt: string;
  comments: Comment[];
}