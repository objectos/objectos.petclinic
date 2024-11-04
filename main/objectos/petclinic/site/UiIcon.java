/*
 * Copyright (C) 2023-2024 Objectos Software LTDA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package objectos.petclinic.site;

enum UiIcon {

  LOGO("""
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24">
  <defs>
  <clipPath id="c"><path d="m193 0h588.7v590h-588.7z"/></clipPath>
  <clipPath id="b"><path d="m193 0h588.7v590h-588.7z"/></clipPath>
  <clipPath id="a"><path d="m193 0h588.7v590h-588.7z"/></clipPath>
  <clipPath id="e"><path d="m193 0h588.7v590h-588.7z"/></clipPath>
  <clipPath id="d"><path d="m28.25 386h423.7v424h-423.7z"/></clipPath>
  </defs>
  <path transform="matrix(.03191 0 0 .02963 -.9245 -.0001288)" d="m769.3 222.2s-0.69-3.79-2.01-11.08c-0.57-3.64-1.64-8.19-2.94-13.59-1.17-5.48-2.98-11.67-5.06-18.65-4.39-13.91-10.86-30.88-21.27-49.6-10.3-18.73-25.03-38.99-45.39-58.12-10.21-9.48-21.68-18.82-34.7-27.17-13.02-8.359-27.46-15.86-43.1-22.25-15.65-6.379-32.52-11.59-50.39-15.34-17.87-3.672-36.72-5.816-56.25-6.32-38.99-0.8672-80.95 4.793-122.5 19.88-20.68 7.64-41.42 17.26-61.13 29.83-19.76 12.51-38.83 27.6-55.51 46-8.29 9.189-16.24 18.99-23.16 29.7-3.66 5.21-6.73 10.88-10.02 16.42-2.92 5.74-6.06 11.43-8.55 17.47-2.8 5.91-4.95 12.13-7.28 18.29-1.91 6.31-4.15 12.57-5.55 19.09-3.36 12.88-5.21 26.22-6.26 39.64-1.1 13.49-0.61 27.04 0.48 40.62 1.56 13.48 3.54 27.02 6.98 40.25l1.21 5.01 0.62 2.55 0.67 2.3 2.59 9.3c0.91 3.15 2.16 6.72 3.25 10.04 1.12 3.48 2.25 6.84 3.58 10.04 4.78 13.09 11 25.78 18.09 38.11 14.11 24.65 32.88 47.12 54.82 66.09 22.26 18.7 47.7 33.77 74.91 44.62 54.61 21.4 115.4 26.45 174 16.09 29.27-5.16 58.13-14.07 85.23-27.25 27-13.23 52.62-30.15 74.35-51.63 21.71-21.37 39.8-46.85 52-75.18 12.46-28.21 18.67-59.06 19.85-89.92 0-3.93 0.35-7.62 0.18-11.7l-0.26-11.32c0-3.76-0.51-7.77-0.74-11.64-0.28-3.92-0.58-7.81-1.15-11.63-1.68-15.41-5.09-30.68-9.4-45.7-8.59-30.09-23.83-58.43-43.75-83-19.9-24.71-45.2-44.88-72.97-60.3-27.78-15.57-58.33-25.92-89.64-31.75-62.63-11.64-128.8-4.808-188.3 19.99-29.59 12.65-57.54 29.74-81.87 51.22-12.07 10.79-23.28 22.58-33.46 35.2-10.12 12.68-19.03 26.26-26.94 40.38-15.75 28.28-26.16 59.06-32.46 90.24-1.27 7.82-2.92 15.61-3.71 23.45l-1.4 11.76-0.86 11.78c-0.73 7.84-0.59 15.63-0.86 23.47-0.1 3.91 0.1 7.81 0.14 11.7l0.13 5.83 0.1 2.92c0.1 1.06-0.11-0.28 0.15 2.7l0.15 1.38c0.42 3.76 0.66 7.18 1.23 11.18 0.64 4.15 1.23 8.43 1.98 12.46 0.85 3.95 1.6 7.93 2.52 11.88 1.03 3.89 1.98 7.84 3.12 11.7 4.54 15.53 10.69 30.53 18.21 44.71 15.02 28.39 35.49 53.27 58.84 73.9 23.38 20.61 49.55 37.31 77.14 49.78 27.56 12.55 56.38 21.3 85.53 26.46 29.15 5.05 58.68 6.59 87.68 4.07 28.99-2.55 57.53-8.98 84.28-19.69 26.76-10.61 51.52-25.9 72.64-44.69 21.13-18.79 38.51-41.19 51.02-65.3 6.29-11.96 11.76-24.49 15.8-36.76l3-8.67 2.88-9.28c2.01-6.15 3.55-12.21 5.21-18.29 6.43-24.27 10.3-49.03 11.34-73.65 0.41-12.3 0.16-24.58-1.2-36.7-0.47-6.04-1.59-12.05-2.47-18.02-1.25-5.93-2.3-11.85-3.96-17.6-3.01-11.62-7.02-22.87-11.9-33.52-4.76-10.73-10.45-20.89-16.88-30.31-12.67-19.01-28.18-35.14-44.81-48.29-16.57-13.34-34.29-23.69-51.93-32.24-17.76-8.325-35.53-14.77-52.98-19.62-17.47-4.757-34.54-8.043-51.01-10.3-32.94-4.46-63.45-4.714-90.73-2.648-54.65 4.047-96 16.96-122.9 27.9-13.48 5.555-23.4 10.63-29.99 14.14-6.52 3.69-9.89 5.58-9.89 5.58l2.08 3.92s3.47-1.58 10.18-4.59c6.78-2.87 16.92-6.96 30.47-11.16 27.01-8.461 68.08-17.69 120.8-18.07 26.27-0.08 55.41 1.863 86.23 7.898 30.77 6.036 63.37 16.15 94.73 32.78 31.16 16.7 60.81 40.89 80.94 73.4 5.06 8.05 9.45 16.66 13.05 25.7 3.69 9 6.68 18.42 8.77 28.21 1.23 4.84 1.88 9.89 2.76 14.87 0.53 5.05 1.3 10.1 1.5 15.28 0.79 10.27 0.49 20.81-0.13 31.43-1.52 21.25-5.46 43.04-11.88 64.79-1.65 5.4-3.2 10.95-5.09 16.2l-2.71 7.97-3.2 8.47c-3.87 10.78-8.6 20.68-14.1 30.51-10.91 19.55-25.28 37.25-42.62 52.15-17.34 14.92-37.72 26.9-60.01 35.42-22.24 8.61-46.38 13.85-71.19 15.78-24.8 1.98-50.38 0.9-75.67-3.49-50.55-8.82-100.5-30.4-140.6-64.44-19.93-17.07-37.14-37.4-49.78-60.4-6.36-11.5-11.54-23.64-15.51-36.24-0.99-3.14-1.83-6.36-2.73-9.54-0.83-3.19-1.53-6.46-2.26-9.7-0.7-3.17-1.12-6.22-1.73-9.33-0.55-3.27-0.92-7.22-1.4-10.8l-0.18-1.38c0.17 2.1 0-0.14 0 0l-0.13-2.64-0.2-5.27c-0.11-3.48-0.37-7.02-0.37-10.51 0.15-7-0.18-14.05 0.37-21.05l0.55-10.49 1.01-10.45c0.57-7 1.91-13.86 2.86-20.82 4.96-27.51 13.62-54.39 26.55-79.21 25.55-49.89 69.14-90.31 121.3-114.9 52.12-24.6 112.5-34.22 170.8-26.18 29.1 4.011 57.79 12.25 84.1 25.44 26.22 13.08 50.25 30.9 69.58 53.08 19.17 22.22 33.9 48.34 42.69 76.46 4.47 14.02 7.79 28.47 9.48 43.11 0.59 3.66 0.89 7.3 1.16 10.93 0.26 3.68 0.74 7.19 0.82 10.99l0.33 11.33c0.18 3.44-0.1 7.28-0.1 10.9-0.74 29.35-6.78 58.47-18.71 84.9-11.85 26.45-29.15 50.17-50.26 69.45-21.09 19.33-45.41 34.89-71.43 45.87-25.98 11.08-53.35 18.26-80.88 21.48-27.52 3.23-55.13 2.88-81.84-1.03-26.67-3.95-52.37-11.52-76.07-22.25-47.46-21.47-85.82-56.98-108.8-99.69-5.7-10.69-10.8-21.64-14.84-32.97-1.12-2.85-2.02-5.59-2.87-8.32-0.86-2.83-1.8-5.33-2.64-8.38l-2.5-9.33-0.61-2.32-0.47-2.13-1.02-4.35c-3.05-11.55-4.68-23.18-6.04-34.7-0.83-11.54-1.49-22.98-0.66-34.24 0.72-11.24 2.1-22.34 4.74-33.08 1.07-5.42 2.88-10.62 4.37-15.87 1.9-5.13 3.56-10.32 5.83-15.25 2-5.05 4.57-9.81 6.94-14.65 2.7-4.65 5.14-9.44 8.21-13.85 5.68-9.07 12.33-17.45 19.27-25.4 27.94-31.88 64-54.48 100.8-69.06 36.92-14.34 74.7-21.59 110.3-22.22 17.82-0.324 35.07 0.946 51.49 3.532 16.46 2.488 32.11 6.363 46.69 11.34 29.27 9.926 54.01 24.66 74 40.82 10.11 7.996 18.62 16.8 26.43 25.29 3.7 4.42 7.48 8.58 10.63 12.98 1.65 2.16 3.28 4.28 4.87 6.37 1.48 2.16 2.93 4.29 4.35 6.39 11.37 16.81 18.84 32.59 24.4 45.59 2.68 6.54 5.07 12.37 6.71 17.49 1.79 5.07 3.28 9.37 4.22 12.99 2.06 7.13 3.12 10.8 3.12 10.8l4.31-0.97" clip-path="url(#e)" fill="#4484cb"/>
  <g stroke="#4484cb">
  <circle transform="matrix(.03191 0 0 .02963 -.9245 -.0001288)" cx="-43.65" cy="47.22" r="1.531" clip-path="url(#a)"/>
  <circle transform="matrix(.03191 0 0 .02963 -.9245 -.0001288)" cx="542" cy="-50.98" r="1.531" clip-path="url(#b)"/>
  <circle transform="matrix(.03191 0 0 .02963 -.9245 -.0001288)" cx="678.8" cy="-28.69" r="1.531" clip-path="url(#c)"/>
  </g>
  <path transform="matrix(.03191 0 0 .02963 -.9245 -.0001288)" d="m40.44 546.6s0.774-2.65 2.258-7.77c0.664-2.59 1.746-5.7 3.016-9.35 1.191-3.68 2.902-7.84 4.828-12.57 3.988-9.31 9.371-20.66 17.53-32.73 1.028-1.5 2.074-3.05 3.141-4.59 1.137-1.51 2.312-3.03 3.488-4.57 2.262-3.17 4.981-6.16 7.645-9.34 5.601-6.1 11.72-12.43 18.99-18.18 14.37-11.61 32.13-22.2 53.16-29.32 10.49-3.58 21.71-6.36 33.54-8.16 11.8-1.85 24.2-2.77 37-2.53 25.57 0.45 52.72 5.65 79.26 15.96 26.4 10.47 52.3 26.71 72.38 49.62 5 5.71 9.77 11.73 13.85 18.25 2.2 3.16 3.95 6.62 5.9 9.95 1.69 3.48 3.56 6.9 4.99 10.52 1.62 3.55 2.83 7.29 4.17 10.97 1.07 3.76 2.39 7.51 3.16 11.4 1.89 7.72 2.89 15.69 3.4 23.75 0.59 8.11 0.11 16.32-0.48 24.62-0.99 8.27-2.15 16.63-4.33 24.93l-0.74 3.12-0.35 1.54-0.44 1.66-1.78 6.7c-0.6 2.19-1.28 3.99-1.91 6.03-0.58 1.96-1.25 3.93-2.06 5.99-2.9 8.13-6.57 16-10.65 23.66-16.52 30.71-44.09 56.21-78.2 71.64-17.01 7.71-35.49 13.15-54.65 15.98-19.18 2.82-39.01 3.06-58.8 0.73-19.76-2.3-39.44-7.46-58.1-15.41-18.7-7.9-36.17-19.07-51.32-32.96-15.18-13.86-27.59-30.89-36.12-49.91-8.559-18.99-12.91-39.89-13.45-61 0-2.59-0.203-5.34-0.07-7.82l0.258-8.14c0.05-2.74 0.406-5.25 0.59-7.9 0.183-2.61 0.402-5.24 0.824-7.85 1.215-10.52 3.602-20.92 6.817-30.99 6.3-20.18 16.9-38.96 30.68-54.92 13.87-15.94 31.13-28.75 49.98-38.15 18.9-9.46 39.51-15.38 60.42-18.26 41.89-5.79 85.27 1.14 122.7 18.81 37.48 17.65 68.82 46.69 87.15 82.53 9.31 17.84 15.52 37.15 19.08 56.93 0.7 4.98 1.66 9.9 2.06 14.95l0.74 7.5 0.38 7.55c0.41 5.01 0.15 10.08 0.26 15.12 0 2.49-0.19 5.05-0.26 7.54l-0.15 3.79-0.1 1.91c0-0.11-0.1 1.51 0 0l-0.13 0.97c-0.33 2.57-0.58 5.42-0.99 7.75-0.44 2.24-0.75 4.43-1.25 6.73-0.53 2.31-1.01 4.66-1.61 6.96-0.67 2.29-1.27 4.59-1.97 6.85-2.87 9.06-6.58 17.76-11.13 26.05-9.1 16.51-21.46 31.13-35.79 43.39-28.78 24.45-64.66 39.97-101 46.29-18.19 3.16-36.56 3.94-54.37 2.51-17.82-1.39-35.16-5.14-51.14-11.33-15.99-6.11-30.64-14.71-43.09-25.42-12.47-10.71-22.8-23.44-30.64-37.48-3.949-7.07-7.347-14.18-10.12-21.93l-2.297-6.08-1.949-5.73c-1.359-3.77-2.476-7.75-3.652-11.65-4.614-15.63-7.461-31.26-8.543-46.55-0.442-7.62-0.66-15.19-0.09-22.57 0.148-3.71 0.699-7.35 1.086-10.99 0.625-3.58 1.082-7.18 1.965-10.67 1.523-7.03 3.656-13.79 6.32-20.28 2.57-6.48 5.731-12.67 9.367-18.46 14.48-23.35 35.76-40.73 58.15-52.72 22.54-11.96 45.96-19.21 68.06-23.57 22.15-4.33 43.08-5.73 61.96-5.67 37.84 0.29 67.36 6.92 86.76 12.98 9.74 3.04 17.03 5.97 21.9 8.03 4.81 2.17 7.31 3.29 7.31 3.29l1.48-2.79s-2.4-1.38-7.09-4.03c-4.74-2.53-11.88-6.17-21.56-10.16-19.29-7.86-48.99-17.13-88.27-20.03-19.6-1.49-41.51-1.31-65.17 1.89-11.85 1.63-24.1 3.98-36.67 7.4-12.54 3.49-25.29 8.12-38.06 14.11-12.65 6.13-25.4 13.55-37.3 23.14-11.96 9.46-23.09 21.04-32.2 34.7-4.609 6.78-8.707 14.08-12.12 21.77-3.508 7.66-6.39 15.74-8.523 24.08-1.192 4.14-1.946 8.4-2.844 12.66-0.625 4.3-1.434 8.6-1.785 12.95-0.973 8.71-1.156 17.53-0.863 26.36 0.753 17.69 3.527 35.47 8.156 52.92 1.195 4.35 2.297 8.73 3.75 13.14l2.074 6.66 2.148 6.25c2.903 8.8 6.836 17.82 11.35 26.39 8.984 17.33 21.48 33.42 36.65 46.92 15.19 13.52 32.97 24.49 52.19 32.11 19.23 7.7 39.73 12.33 60.56 14.15 20.85 1.82 42.05 0.7 63.01-2.93 20.92-3.71 41.64-9.99 61.44-18.99 19.81-8.96 38.63-20.98 55.42-35.78 16.78-14.83 31.47-32.7 42.27-53.09 5.4-10.19 9.83-20.98 13.08-32.13 0.83-2.77 1.51-5.62 2.24-8.41 0.68-2.83 1.21-5.7 1.82-8.52 0.55-2.89 0.96-5.97 1.43-8.95 0.41-2.88 0.57-5.34 0.87-8.05l0.11-0.99c0.18-2.15 0.1-1.19 0.11-1.94l0.1-2.1 0.1-4.19c0-2.79 0.17-5.58 0.11-8.41-0.2-5.62-0.1-11.22-0.64-16.84l-0.6-8.47-1-8.46c-0.59-5.63-1.74-11.23-2.68-16.85-4.52-22.4-12.01-44.52-23.31-64.83-5.68-10.14-12.09-19.9-19.38-29.01-7.29-9.07-15.36-17.54-24.03-25.29-17.47-15.43-37.54-27.72-58.81-36.82-42.73-17.81-90.27-22.72-135.3-14.36-22.48 4.19-44.43 11.63-64.4 22.83-19.95 11.06-38.11 25.57-52.42 43.32-14.31 17.65-25.26 38.02-31.43 59.64-3.102 10.79-5.547 21.75-6.738 32.83-0.426 2.74-0.645 5.55-0.848 8.36-0.164 2.77-0.496 5.66-0.531 8.36l-0.184 8.13c-0.109 2.94 0.09 5.59 0.129 8.4 0.844 22.17 5.309 44.34 14.25 64.6 8.781 20.36 21.77 38.67 37.36 54.03 15.61 15.43 34.02 27.59 53.43 37.09 19.46 9.48 40.2 15.87 61.23 19.58 42.05 7.44 85.77 3.82 125-11.56 19.57-7.8 37.82-18.62 53.82-32.05 15.77-13.65 29.27-29.78 39.39-47.49 5.09-8.85 9.57-17.98 13.01-27.39 0.93-2.31 1.78-4.71 2.57-7.2 0.77-2.38 1.69-4.96 2.33-7.21l1.87-6.69 0.46-1.65 0.46-1.84 0.88-3.6c2.46-9.5 3.88-19.24 5-28.92 0.79-9.75 1.14-19.48 0.35-29.18-0.76-9.65-2.1-19.22-4.5-28.48-1.01-4.68-2.61-9.18-3.99-13.72-1.67-4.42-3.21-8.89-5.22-13.13-1.8-4.36-4.05-8.43-6.17-12.55-2.35-3.99-4.55-8.06-7.18-11.81-4.98-7.68-10.69-14.73-16.64-21.33-12-13.23-25.68-24.08-39.88-33.06-14.17-9.02-29.06-15.95-43.92-21.42-29.84-10.86-59.98-14.92-88.01-14.29-14.02 0.35-27.58 1.89-40.41 4.53-12.83 2.71-24.95 6.45-36.19 11.03-11.24 4.59-21.6 9.97-30.98 15.98-9.36 6-17.59 12.71-24.92 19.52-14.62 13.74-25.2 28.31-32.62 41.76-7.476 13.44-12.12 25.64-15.28 35.64-1.488 5.01-2.793 9.46-3.621 13.39-0.938 3.89-1.707 7.16-2.129 9.77-0.938 5.23-1.434 7.95-1.434 7.95l3.086 0.72" clip-path="url(#d)" fill="#59606a"/>
  </svg>
  """),

  HOME("""
  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" width="20" height="20" stroke-width="1.5" stroke="currentColor">
  <path stroke-linecap="round" stroke-linejoin="round" d="m2.25 12 8.954-8.955c.44-.439 1.152-.439 1.591 0L21.75 12M4.5 9.75v10.125c0 .621.504 1.125 1.125 1.125H9.75v-4.875c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125V21h4.125c.621 0 1.125-.504 1.125-1.125V9.75M8.25 21h8.25" />
  </svg>
  """),

  OWNERS("""
  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" width="20" height="20" stroke-width="1.5" stroke="currentColor">
  <path stroke-linecap="round" stroke-linejoin="round" d="M15 19.128a9.38 9.38 0 0 0 2.625.372 9.337 9.337 0 0 0 4.121-.952 4.125 4.125 0 0 0-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 0 1 8.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0 1 11.964-3.07M12 6.375a3.375 3.375 0 1 1-6.75 0 3.375 3.375 0 0 1 6.75 0Zm8.25 2.25a2.625 2.625 0 1 1-5.25 0 2.625 2.625 0 0 1 5.25 0Z" />
  </svg>
  """);

  final String value;

  private UiIcon(String value) {
    this.value = value;
  }

}