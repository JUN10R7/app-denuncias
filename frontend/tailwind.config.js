export default {
  darkMode: 'class',
  content: ['./src/**/*.{html,ts}'],
  theme: {
    extend: {
      colors: {
        primary: {
          light: '#0F4C81',
          dark: '#3B82F6',
        },
        secondary: {
          light: '#EAB308',
          dark: '#FCD34D',
        },
        background: {
          light: '#F3F4F6',
          dark: '#1F2937',
        },
        surface: {
          light: '#FFFFFF',
          dark: '#111827',
        },
        text: {
          light: '#111827',
          dark: '#F3F4F6',
        },
        muted: {
          light: '#6B7280',
          dark: '#9CA3AF',
        },
        error: {
          light: '#DC2626',
          dark: '#F87171',
        },
      },
    },
  },
  plugins: [],
}